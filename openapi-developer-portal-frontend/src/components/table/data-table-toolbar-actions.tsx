import { DotsHorizontalIcon, PlusCircledIcon, PlusIcon, TrashIcon } from "@radix-ui/react-icons";
import React from "react";
import { DatabaseController } from "../../lib/api";
import { Button } from "../ui/button";
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuSeparator, DropdownMenuShortcut, DropdownMenuTrigger } from "../ui/dropdown-menu";
import { Sheet, SheetContent, SheetDescription, SheetHeader, SheetTitle } from "../ui/sheet";
import { MicroserviceForm } from "./data-table-create-form";

interface Microservice {
    id: string;
    title: string;
    description: string;
    category: string;
    name: string;
    email: string;
    date: string;
    version: string;
    dependencies: string[];
    specification: string;
}

export function DataTableToolbarActions() {
    const [open, setIsOpen] = React.useState(false);
    const { add, clear } = DatabaseController();

    return (
        <>
            <DropdownMenu>
                <DropdownMenuTrigger asChild>
                    <Button variant="outline" className="flex h-8 w-8 p-0 data-[state=open]:bg-muted ">
                        <DotsHorizontalIcon className="h-4 w-8" />
                        <span className="sr-only">Open menu</span>
                    </Button>
                </DropdownMenuTrigger>
                <DropdownMenuContent align="end" className="w-m[1250px]">
                    <DropdownMenuItem onClick={() => setIsOpen(true)}>
                        New
                        <DropdownMenuShortcut>
                            <PlusIcon />
                        </DropdownMenuShortcut>
                    </DropdownMenuItem>
                    <DropdownMenuItem onClick={() => add()}>
                        Add
                        <DropdownMenuShortcut>
                            <PlusCircledIcon />
                        </DropdownMenuShortcut>
                    </DropdownMenuItem>
                    <DropdownMenuSeparator />
                    <DropdownMenuItem onClick={() => clear()}>
                        Clear
                        <DropdownMenuShortcut>
                            <TrashIcon />
                        </DropdownMenuShortcut>
                    </DropdownMenuItem>
                </DropdownMenuContent>
            </DropdownMenu>
            <Sheet open={open} onOpenChange={setIsOpen}>
                <SheetContent className="min-w-[600px]">
                    <SheetHeader>
                        <SheetTitle>Create Microservice</SheetTitle>
                        <SheetDescription>Enter the details of your microservice and click create.</SheetDescription>
                        <MicroserviceForm />
                    </SheetHeader>
                </SheetContent>
            </Sheet>
        </>
    );
}
