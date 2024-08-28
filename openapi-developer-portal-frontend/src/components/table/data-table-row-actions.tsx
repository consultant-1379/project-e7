import { DialogDescription } from "@radix-ui/react-dialog";
import { DotsHorizontalIcon, EyeOpenIcon, FileTextIcon, LinkBreak2Icon } from "@radix-ui/react-icons";
import { Row } from "@tanstack/react-table";
import React from "react";
import { DatabaseController } from "../../lib/api";
import { Button } from "../ui/button";
import { Dialog, DialogContent, DialogHeader } from "../ui/dialog";
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuSeparator, DropdownMenuShortcut, DropdownMenuTrigger } from "../ui/dropdown-menu";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "../ui/tabs";
import { Dependencies } from "./data-table-d3";
import { DailyUsage } from "./data-table-details";

interface DataTableRowActionsProps<TData> {
    row: Row<TData>;
}

export function DataTableRowActions<TData>({ row }: DataTableRowActionsProps<TData>) {
    const [open, setIsOpen] = React.useState(false);

    const { add, remove, view } = DatabaseController();

    return (
        <>
            <DropdownMenu>
                <DropdownMenuTrigger asChild>
                    <Button variant="ghost" className="flex h-8 w-8 p-0 data-[state=open]:bg-muted">
                        <DotsHorizontalIcon className="h-4 w-4" />
                        <span className="sr-only">Open menu</span>
                    </Button>
                </DropdownMenuTrigger>
                <DropdownMenuContent align="end" className=" ">
                    <DropdownMenuItem
                        onSelect={() => {
                            setIsOpen(true);
                        }}
                    >
                        View
                        <DropdownMenuShortcut>
                            <EyeOpenIcon />
                        </DropdownMenuShortcut>
                    </DropdownMenuItem>
                    <DropdownMenuSeparator />
                    <DropdownMenuItem onSelect={() => remove(row.getValue("title"))}>
                        Delete
                        <DropdownMenuShortcut>
                            <LinkBreak2Icon />
                        </DropdownMenuShortcut>
                    </DropdownMenuItem>

                    <DropdownMenuSeparator />
                    <DropdownMenuItem onSelect={() => view(row.getValue("title"))}>
                        Swagger
                        <DropdownMenuShortcut>
                            <FileTextIcon />
                        </DropdownMenuShortcut>
                    </DropdownMenuItem>
                </DropdownMenuContent>
            </DropdownMenu>
            <Dialog open={open} onOpenChange={setIsOpen}>
                <DialogContent className=" min-w-[700px] min-h-[500px] flex flex-col">
                    <DialogHeader className="font-medium leading-none">{row.getValue("title")}</DialogHeader>
                    <Tabs defaultValue="details" className="w-full">
                        <TabsList className="grid w-full grid-cols-2">
                            <TabsTrigger value="daily">Daily Usage</TabsTrigger>
                            <TabsTrigger value="dependencies">Dependencies</TabsTrigger>
                        </TabsList>
                        <TabsContent value="daily">
                            <DialogDescription className="text-sm text-gray-500 h-[500px] w-full rounded-md border">
                                <DailyUsage />
                            </DialogDescription>
                        </TabsContent>
                        <TabsContent value="dependencies">
                            <DialogDescription className="text-sm text-gray-500 w-full rounded-md border overflow-hidden max-h-[500px]">
                                <Dependencies />
                            </DialogDescription>
                        </TabsContent>
                    </Tabs>
                </DialogContent>
            </Dialog>
        </>
    );
}
