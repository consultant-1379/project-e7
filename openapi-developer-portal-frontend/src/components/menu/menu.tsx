"use client";

import * as React from "react";
import { cn } from "../../lib/utils";
import { NavigationMenu, NavigationMenuContent, NavigationMenuItem, NavigationMenuLink, NavigationMenuList, NavigationMenuTrigger } from "../ui/navigation-menu";

const architecture: { title: string; href: string; description: string }[] = [
    {
        title: "Spring Boot",
        href: "https://spring.io/projects/spring-boot",
        description:
            "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can just run. We take an opinionated view of the Spring platform and third-party libraries so you can get started with minimum fuss. Most Spring Boot applications need minimal Spring configuration.",
    },
    {
        title: "MongoDB",
        href: "https://www.mongodb.com/",
        description: "MongoDB is a general purpose, document-based, distributed database built for modern application developers and for the cloud era.",
    },
    {
        title: "React",
        href: "https://reactjs.org/",
        description: "A JavaScript library for building user interfaces.",
    },
    {
        title: "Tailwind CSS",
        href: "https://tailwindcss.com/",
        description: "A utility-first CSS framework for rapidly building custom user interfaces.",
    },
    {
        title: "Kubernetes",
        href: "https://kubernetes.io/",
        description: "Kubernetes is an open-source system for automating deployment, scaling, and management of containerized applications.",
    },
    {
        title: "D3 by Observable",
        href: "https://d3js.org/",
        description: "D3.js is a JavaScript library for manipulating documents based on data. D3 helps you bring data to life using HTML, SVG, and CSS.",
    },
];
const documentation: { title: string; href: string; description: string }[] = [
    {
        title: "Jira",
        href: "https://jira-oss.seli.wh.rnd.internal.ericsson.com/secure/RapidBoard.jspa?rapidView=8970&quickFilter=61119",
        description: "Jira is a proprietary issue tracking product developed by Atlassian that allows bug tracking and agile project management.",
    },
    {
        title: "Swagger API",
        href: "http://localhost:8080/swagger-ui/index.html#/",
        description: "Where you can find all the API endpoints as well as test them out.",
    },
    {
        title: "Ericcson API",
        href: "https://iot.developer.ericsson.com/en/learn/api-documentation",
        description: "This section describes the APIs exposed by IoT Accelerator: capabilities, logical entities, interfaces and protocols.",
    },
    {
        title: "Gerrit",
        href: "https://gerrit.ericsson.se/#/admin/projects/OSS/ENM-Parent/SQ-Gate/com.ericsson.graduates/project-e7",
        description:
            "Gerrit is a free, web-based team code collaboration tool. Software developers in a team can review each other's modifications on their source code using a Web browser and approve or reject those changes.",
    },
    {
        title: "SonarQube",
        href: "http://localhost:9000/",
        description:
            "SonarQube is an open-source platform developed by SonarSource for continuous inspection of code quality to perform automatic reviews with static analysis of code to detect bugs, code smells, and security vulnerabilities on 20+ programming languages.",
    },
];

export function Menu() {
    return (
        <NavigationMenu>
            <NavigationMenuList>
                <NavigationMenuItem>
                    <NavigationMenuTrigger>Documentation</NavigationMenuTrigger>
                    <NavigationMenuContent>
                        <ul className="grid w-[400px] gap-3 p-4 md:w-[500px] md:grid-cols-2 lg:w-[600px] ">
                            {documentation.map((component) => (
                                <ListItem key={component.title} title={component.title} href={component.href} target="_blank" rel="noopener noreferrer">
                                    {component.description}
                                </ListItem>
                            ))}
                        </ul>
                    </NavigationMenuContent>
                </NavigationMenuItem>
                <NavigationMenuItem>
                    <NavigationMenuTrigger>Architecture</NavigationMenuTrigger>
                    <NavigationMenuContent>
                        <ul className="grid w-[400px] gap-3 p-4 md:w-[500px] md:grid-cols-2 lg:w-[600px] ">
                            {architecture.map((component) => (
                                <ListItem key={component.title} title={component.title} href={component.href} target="_blank" rel="noopener noreferrer">
                                    {component.description}
                                </ListItem>
                            ))}
                        </ul>
                    </NavigationMenuContent>
                </NavigationMenuItem>
            </NavigationMenuList>
        </NavigationMenu>
    );
}

const ListItem = React.forwardRef<React.ElementRef<"a">, React.ComponentPropsWithoutRef<"a">>(({ className, title, children, ...props }, ref) => {
    return (
        <li>
            <NavigationMenuLink asChild>
                <a
                    ref={ref}
                    className={cn(
                        "block select-none space-y-1 rounded-md p-3 leading-none no-underline outline-none transition-colors hover:bg-accent hover:text-accent-foreground focus:bg-accent focus:text-accent-foreground",
                        className
                    )}
                    {...props}
                >
                    <div className="text-sm font-medium leading-none">{title}</div>
                    <p className="line-clamp-2 text-sm leading-snug text-muted-foreground">{children}</p>
                </a>
            </NavigationMenuLink>
        </li>
    );
});
ListItem.displayName = "ListItem";
