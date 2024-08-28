"use client";

import { ColumnDef } from "@tanstack/react-table";

import { Checkbox } from "../ui/checkbox";

import { categories } from "../../data/data";
import { Microservice } from "../../data/schema";

import { CalendarIcon } from "@radix-ui/react-icons";
import { DatabaseController } from "../../lib/api";
import { FormattedDate } from "../../lib/date";
import { Badge } from "../ui/badge";
import { HoverCard, HoverCardContent, HoverCardTrigger } from "../ui/hover-card";
import { DataTableColumnHeader } from "./data-table-column-header";
import { DataTableRowActions } from "./data-table-row-actions";

export const Columns: ColumnDef<Microservice>[] = [
    {
        id: "select",
        header: ({ table }) => (
            <Checkbox
                checked={table.getIsAllPageRowsSelected()}
                onCheckedChange={(value) => table.toggleAllPageRowsSelected(!!value)}
                aria-label="Select all"
                className="translate-y-[2px] mx-2"
            />
        ),
        cell: ({ row }) => (
            <Checkbox checked={row.getIsSelected()} onCheckedChange={(value) => row.toggleSelected(!!value)} aria-label="Select row" className="translate-y-[2px] mx-2" />
        ),
        enableSorting: false,
        enableHiding: false,
    },
    {
        accessorKey: "id",
        header: ({ column }) => <DataTableColumnHeader column={column} title="ID" />,
        cell: ({ row }) => (
            <div className="max-w-[500px]">
                <span>{row.getValue("id")}</span>
            </div>
        ),
        enableSorting: true,
        enableHiding: false,
    },
    {
        accessorKey: "category",
        header: ({ column }) => <DataTableColumnHeader column={column} title="Category" />,
        cell: ({ row }) => {
            const category = categories.find((category) => category.value === row.getValue("category"));

            if (!category) {
                return null;
            }

            return (
                <div className="flex items-center">
                    {category.icon && <category.icon className="mr-2 h-4 w-4 text-muted-foreground" />}
                    <span>{category.label}</span>
                </div>
            );
        },
        filterFn: (row, id, value) => {
            return value.includes(row.getValue(id));
        },
    },
    {
        accessorKey: "title",
        header: ({ column }) => <DataTableColumnHeader column={column} title="Microservice" />,
        cell: ({ row }) => {
            return (
                <div className="truncate max-w-[200px]">
                    <span>{row.getValue("title")}</span>{" "}
                </div>
            );
        },
        enableSorting: true,
        enableHiding: false,
    },

    {
        accessorKey: "description",
        header: ({ column }) => <DataTableColumnHeader column={column} title="Description" />,
        cell: ({ row }) => {
            const { version } = DatabaseController();
            const category = categories.find((category) => category.value === row.getValue("category"));

            return (
                <HoverCard>
                    <HoverCardTrigger>
                        <div className="flex items-center space-x-2">
                            <Badge variant="outline">{version(row.getValue("title"))}</Badge>
                            <div className="w-[500px] truncate">
                                <span>{row.getValue("description")}</span>{" "}
                            </div>
                        </div>
                    </HoverCardTrigger>
                    <HoverCardContent className=" w-fit">
                        <div className="flex justify-between space-x-4">
                            <div className="space-y-1">
                                <div className="flex items-center space-x-2">
                                    <h4 className="text-sm font-semibold">
                                        {row.getValue("title")} v{version(row.getValue("title"))}
                                    </h4>
                                </div>
                                <p className="text-sm  max-w-sm ">{row.getValue("description")}</p>
                                <div className="flex items-center pt-2 space-x-4">
                                    <div className="flex items-center pt-2">
                                        <CalendarIcon className="mr-1 h-4 w-4 opacity-70" />
                                        <span className="text-xs text-muted-foreground">
                                            <FormattedDate date={row.getValue("date")} />
                                        </span>
                                    </div>
                                    <div className="flex items-center pt-2">
                                        {category?.icon && <category.icon className="mr-1 h-4 w-4 opacity-70" />}
                                        <span className="text-xs text-muted-foreground">{category?.label}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </HoverCardContent>
                </HoverCard>
            );
        },
    },
    {
        accessorKey: "name",
        header: ({ column }) => <DataTableColumnHeader column={column} title="Name" />,
        cell: ({ row }) => {
            return (
                <div className="flex space-x-2">
                    <span className="max-w-[500px] truncate ">{row.getValue("name")}</span>
                </div>
            );
        },
    },
    {
        accessorKey: "email",
        header: ({ column }) => <DataTableColumnHeader column={column} title="Email" />,
        cell: ({ row }) => {
            return (
                <div className="flex space-x-2">
                    <span className="max-w-[250px] truncate lowercase ">{row.getValue("email")}</span>
                </div>
            );
        },
    },
    {
        accessorKey: "date",
        header: ({ column }) => <DataTableColumnHeader column={column} title="Date" />,
        cell: ({ row }) => {
            return (
                <div className="flex space-x-2">
                    <span className="max-w-[500px] truncate ">
                        <FormattedDate date={row.getValue("date")} />
                    </span>
                </div>
            );
        },
    },
    {
        id: "actions",
        cell: ({ row }) => <DataTableRowActions row={row} />,
    },
];
