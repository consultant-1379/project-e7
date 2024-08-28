import axios from "axios";
import React from "react";
import { Microservice } from "../data/schema";

export function DatabaseController() {
    const [microservices, setMicroservices] = React.useState<Microservice[]>([]);

    React.useEffect(() => {
        async function fetchData() {
            try {
                const response = await axios.get("http://localhost:8080/api/get/source");
                setMicroservices(response.data);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        }
        fetchData();
    }, []);

    function titles() {
        const titles = microservices.map((microservice) => microservice.title);
        return titles;
    }

    function add() {
        for (let i = 0; i < 10; i++) {
            axios.post(`http://localhost:8080/api/create/random`);
        }

        window.location.reload();
    }

    function clear() {
        axios.delete(`http://localhost:8080/api/delete/all`);
        window.location.reload();
    }

    function post(title: string, description: string, category: string, name: string, email: string, version: string, dependencies: string, specification: string) {
        title = encodeURIComponent(title);
        description = encodeURIComponent(description);
        category = encodeURIComponent(category);
        name = encodeURIComponent(name);
        email = encodeURIComponent(email);
        version = encodeURIComponent(version);
        dependencies = encodeURIComponent(dependencies);
        specification = encodeURIComponent(specification);

        axios
            .post(`http://localhost:8080/api/create/new/${title}/${description}/${category}/${name}/${email}/${version}/${dependencies}/${specification}`)
            .then((response) => {
                console.log("Response status:", response.status);
                console.log("Response data:", response.data);
                console.log("Response headers:", response.headers);
            })
            .catch(function (error) {
                if (error.response) {
                    console.warn("Response data:", error.response.data);
                    console.warn("Response status:", error.response.status);
                    console.warn("Response headers:", error.response.headers);
                } else if (error.request) {
                    console.warn("Request:", error.request);
                } else {
                    console.warn("Error:", error.message);
                }
                console.warn("Config:", error.config);
            });
        window.location.reload();
    }

    function remove(title: string) {
        const id = microservices.find((microservice) => microservice.title === title)?.id;
        axios.delete(`http://localhost:8080/api/delete/id/${id}`);
        window.location.reload();
    }

    function view(title: string) {
        const specification = microservices.find((microservice) => microservice.title === title)?.specification;
        window.open(specification, "_blank");
    }

    function dependencies(title: string) {
        const dependencies = microservices.find((microservice) => microservice.title === title)?.dependencies;
        return dependencies;
    }

    function category(title: string) {
        const category = microservices.find((microservice) => microservice.title === title)?.category;
        return category;
    }

    function date(title: string) {
        const date = microservices.find((microservice) => microservice.title === title)?.date;
        return date;
    }

    function version(title: string) {
        const version = microservices.find((microservice) => microservice.title === title)?.version;
        return version;
    }

    function name(title: string) {
        const name = microservices.find((microservice) => microservice.title === title)?.name;
        return name;
    }

    function email(title: string) {
        const email = microservices.find((microservice) => microservice.title === title)?.email;
        return email;
    }

    function description(title: string) {
        const description = microservices.find((microservice) => microservice.title === title)?.description;
        return description;
    }

    return {
        microservices,
        clear,
        post,
        titles,
        add,
        remove,
        view,
        dependencies,
        category,
        date,
        version,
        name,
        email,
        description,
    };
}
