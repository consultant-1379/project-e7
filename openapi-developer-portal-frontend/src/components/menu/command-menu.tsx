/* eslint-disable react/jsx-pascal-case */
import { DialogProps } from "@radix-ui/react-alert-dialog";
import { ActivityLogIcon, BarChartIcon, ExternalLinkIcon, FileTextIcon, LightningBoltIcon, PaperPlaneIcon, ReaderIcon, RocketIcon } from "@radix-ui/react-icons";
import * as React from "react";
import { cn } from "../../lib/utils";
import { Button } from "../ui/button";
import { CommandDialog, CommandEmpty, CommandGroup, CommandInput, CommandItem, CommandList, CommandShortcut } from "../ui/command";
import { Icons } from "../ui/icons";

const links: { title: string; href: string; description: string; icon: JSX.Element; shortcut?: string }[] = [
    {
        title: "Spring Boot",
        href: "https://spring.io/projects/spring-boot",
        description:
            "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can just run. We take an opinionated view of the Spring platform and third-party libraries so you can get started with minimum fuss. Most Spring Boot applications need minimal Spring configuration.",
        icon: <RocketIcon className="mr-2 h-4 w-4" />,
        shortcut: "⌘S",
    },
    {
        title: "MongoDB",
        href: "https://www.mongodb.com/",
        description: "MongoDB is a general purpose, document-based, distributed database built for modern application developers and for the cloud era.",
        icon: <PaperPlaneIcon className="mr-2 h-4 w-4" />,
        shortcut: "⌘M",
    },
    {
        title: "React",
        href: "https://reactjs.org/",
        description: "A JavaScript library for building user interfaces.",
        icon: <Icons.react className="mr-2 h-4 w-4" />,
        shortcut: "⌘R",
    },
    {
        title: "Tailwind CSS",
        href: "https://tailwindcss.com/",
        description: "A utility-first CSS framework for rapidly building custom user interfaces.",
        icon: <Icons.tailwind className="mr-2 h-4 w-4" />,
        shortcut: "⌘T",
    },
    {
        title: "Kubernetes",
        href: "https://kubernetes.io/",
        description: "Kubernetes is an open-source system for automating deployment, scaling, and management of containerized applications.",
        icon: <ExternalLinkIcon className="mr-2 h-4 w-4" />,
        shortcut: "⌘8",
    },
    {
        title: "D3 by Observable",
        href: "https://d3js.org/",
        description: "D3.js is a JavaScript library for manipulating documents based on data. D3 helps you bring data to life using HTML, SVG, and CSS.",
        icon: <BarChartIcon className="mr-2 h-4 w-4" />,
        shortcut: "⌘D",
    },
    {
        title: "Jira",
        href: "https://jira-oss.seli.wh.rnd.internal.ericsson.com/secure/RapidBoard.jspa?rapidView=8970&quickFilter=61119",
        description: "Jira is a proprietary issue tracking product developed by Atlassian that allows bug tracking and agile project management.",
        icon: <FileTextIcon className="mr-2 h-4 w-4" />,
        shortcut: "⌘X",
    },
    {
        title: "SonarQube",
        href: "http://localhost:9000/",
        description:
            "SonarQube is an open-source platform developed by SonarSource for continuous inspection of code quality to perform automatic reviews with static analysis of code to detect bugs, code smells, and security vulnerabilities on 20+ programming languages.",
        icon: <ActivityLogIcon className="mr-2 h-4 w-4" />,
        shortcut: "⌘Q",
    },
    {
        title: "Swagger API",
        href: "http://localhost:8080/swagger-ui/index.html#/",
        description: "Where you can find all the API endpoints as well as test them out.",
        icon: <LightningBoltIcon className="mr-2 h-4 w-4" />,
        shortcut: "⌘Y",
    },
    {
        title: "Ericcson API",
        href: "https://iot.developer.ericsson.com/en/learn/api-documentation",
        description: "This section describes the APIs exposed by IoT Accelerator: capabilities, logical entities, interfaces and protocols.",
        icon: <ReaderIcon className="mr-2 h-4 w-4" />,
        shortcut: "⌘Z",
    },
    {
        title: "Gerrit",
        href: "https://gerrit.ericsson.se/#/admin/projects/OSS/ENM-Parent/SQ-Gate/com.ericsson.graduates/project-e7",
        description:
            "Gerrit is a free, web-based team code collaboration tool. Software developers in a team can review each other's modifications on their source code using a Web browser and approve or reject those changes.",
        icon: <ActivityLogIcon className="mr-2 h-4 w-4" />,
        shortcut: "⌘G",
    },
];

export function CommandMenu({ ...props }: DialogProps) {
    const [open, setOpen] = React.useState(false);

    React.useEffect(() => {
        const down = (e: KeyboardEvent) => {
            if (e.key === "k" && (e.metaKey || e.ctrlKey)) {
                e.preventDefault();
                setOpen((open) => !open);
            }
        };

        document.addEventListener("keydown", down);
        return () => document.removeEventListener("keydown", down);
    }, []);

    const runCommand = React.useCallback((command: () => unknown) => {
        setOpen(false);
        command();
    }, []);

    return (
        <>
            <Button
                variant="outline"
                className={cn("relative w-full justify-start text-sm text-muted-foreground sm:pr-12 md:w-40 lg:w-64")}
                onClick={() => setOpen(true)}
                {...props}
            >
                <span className="hidden lg:inline-flex">Search portal...</span>
                <span className="inline-flex lg:hidden">Search...</span>
                <kbd className="pointer-events-none absolute right-1.5 top-1.5 hidden h-5 select-none items-center gap-1 rounded border bg-muted px-1.5 font-mono text-[10px] font-medium opacity-100 sm:flex">
                    <span className="text-xs">⌘</span>K
                </kbd>
            </Button>
            <CommandDialog open={open} onOpenChange={setOpen}>
                <CommandInput placeholder="Type a command or search..." />
                <CommandList>
                    <CommandEmpty>No results found.</CommandEmpty>

                    <CommandGroup heading="Quick Links">
                        {links.map((link) => (
                            <CommandItem key={link.title} onSelect={() => runCommand(() => window.open(link.href, "_blank"))} className="flex items-center">
                                <span>{link.title}</span>
                                <CommandShortcut>{link.shortcut}</CommandShortcut>
                            </CommandItem>
                        ))}
                    </CommandGroup>
                </CommandList>
            </CommandDialog>
        </>
    );
}
