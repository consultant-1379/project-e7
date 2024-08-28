/* eslint-disable no-restricted-globals */
import * as d3 from "d3";
import { zoom } from "d3-zoom";
import React from "react";
import { DatabaseController } from "../../lib/api";

export function Dependencies() {
    const svgRef = React.useRef(null);

    const { microservices, category } = DatabaseController();

    React.useEffect(() => {
        const categories = Array.from(new Set(microservices.map((m) => m.category)));
        const nodes = microservices.map((microservice) => ({ id: microservice.title }));
        const links = [];

        microservices.forEach((microservice) => {
            microservice.dependencies.forEach((dependency) => {
                links.push({ source: microservice.title, target: dependency });
            });
        });
        const color = d3.scaleOrdinal().domain(categories).range(d3.schemeCategory10);

        const width = 650 * 2;
        const height = 500 * 2;

        const simulation = d3
            .forceSimulation(nodes)
            .force(
                "link",
                d3
                    .forceLink(links)
                    .id((d) => d.id)
                    .distance(100)
            )
            .alphaDecay(0.01)
            .force("charge", d3.forceManyBody().strength(-500))
            .force("x", d3.forceX().strength(0.1))
            .force("y", d3.forceY().strength(0.1));

        const zoomBehavior = zoom()
            .scaleExtent([0.5, 5])
            .on("zoom", zoomed)
            .filter((event) => !event.ctrlKey && !event.button);

        const svg = d3
            .select(svgRef.current)
            .attr("viewBox", [-width / 2, -height / 2, width, height])
            .attr("width", width)
            .attr("height", height)
            .attr("style", "max-width: 100%; height: auto; font: 12px sans-serif;");

        svg.append("defs")
            .selectAll("marker")
            .data(categories)
            .join("marker")
            .attr("id", (d) => `arrow-${d}`)
            .attr("viewBox", "0 -5 10 10")
            .attr("refX", 15)
            .attr("refY", -0.5)
            .attr("markerWidth", 6)
            .attr("markerHeight", 6)
            .attr("orient", "auto")
            .append("path")
            .attr("fill", color)
            .attr("d", "M0,-5L10,0L0,5");

        const link = svg
            .append("g")
            .attr("fill", "none")
            .attr("stroke-width", 2)
            .selectAll("path")
            .data(links)
            .enter()
            .append("path")
            .attr("stroke", (d) => {
                const gradient = svg
                    .append("linearGradient")
                    .attr("id", `gradient-${d.source.id}-${d.target.id}`)
                    .attr("gradientUnits", "userSpaceOnUse")
                    .attr("x1", d.source.x)
                    .attr("y1", d.source.y)
                    .attr("x2", d.target.x)
                    .attr("y2", d.target.y);

                gradient
                    .append("stop")
                    .attr("offset", "0%")
                    .attr("stop-color", color(category(d.source.id)));

                gradient
                    .append("stop")
                    .attr("offset", "100%")
                    .attr("stop-color", color(category(d.target.id)));

                return `url(${new URL(`#gradient-${d.source.id}-${d.target.id}`, location)})`;
            })
            .attr("marker-end", (d) => `url(${new URL(`#arrow-${category(d.target)}`, location)})`);

        const linkGroup = svg.append("g").attr("class", "links");
        const nodeGroup = svg.append("g").attr("class", "nodes");

        svg.call(zoomBehavior);

        const node = svg
            .append("g")
            .attr("fill", "currentColor")
            .attr("stroke-linecap", "round")
            .attr("stroke-linejoin", "round")
            .selectAll("g")
            .data(nodes)
            .join("g")
            .call(drag(simulation));

        node.append("circle")
            .attr("stroke", "white")
            .attr("stroke-width", 2)
            .attr("r", 6)
            .attr("fill", (d) => color(category(d.id)));

        node.append("text")
            .style("text-anchor", "middle")
            .attr("y", -15)
            .text((d) => d.id)
            .clone(true)
            .lower()
            .attr("fill", "none")
            .attr("stroke", "white")
            .attr("stroke-width", 3);

        function zoomed(event) {
            const { transform } = event;
            const currentZoom = transform.k;
            const adjustedX = transform.x / currentZoom;
            const adjustedY = transform.y / currentZoom;

            svg.attr("transform", `translate(${adjustedX},${adjustedY}) scale(${currentZoom})`);
            nodeGroup.attr("transform", `translate(${adjustedX},${adjustedY}) scale(${currentZoom})`);
            linkGroup.attr("transform", `translate(${adjustedX},${adjustedY}) scale(${currentZoom})`);
        }

        simulation.on("tick", () => {
            link.attr("d", linkArc);
            node.attr("transform", (d) => {
                const x = Math.max(-width / 2, Math.min(width / 2, d.x));
                const y = Math.max(-height / 2, Math.min(height / 2, d.y));
                return `translate(${x},${y})`;
            });
        });

        d3.timer(() => {
            link.attr("d", linkArc);
            node.attr("transform", (d) => {
                const x = Math.max(-width / 2, Math.min(width / 2, d.x));
                const y = Math.max(-height / 2, Math.min(height / 2, d.y));
                return `translate(${x},${y})`;
            });
            return simulation.alpha() > 0.005;
        });
    }, [category, microservices]);

    function linkArc(d) {
        const dx = d.target.x - d.source.x;
        const dy = d.target.y - d.source.y;
        const dr = Math.sqrt(dx * dx + dy * dy);

        const radius = dr * 2;

        return `
        M${d.source.x},${d.source.y}
        A${radius},${radius} 0 0,1 ${d.target.x},${d.target.y}
    `;
    }

    function drag(simulation) {
        function dragstarted(event, d) {
            if (!event.active) simulation.alphaTarget(0.3).restart();
            d.fx = d.x;
            d.fy = d.y;
        }

        function dragged(event, d) {
            d.fx = event.x;
            d.fy = event.y;
        }

        function dragended(event, d) {
            if (!event.active) simulation.alphaTarget(0);
            d.fx = null;
            d.fy = null;
        }

        return d3.drag().on("start", dragstarted).on("drag", dragged).on("end", dragended);
    }

    return <svg ref={svgRef} />;
}
