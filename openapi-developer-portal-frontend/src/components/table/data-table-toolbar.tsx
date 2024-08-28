import { Table } from "@tanstack/react-table";

import { categories } from "../../data/data";
import { Input } from "../ui/input";
import { DataTableFacetedFilter } from "./data-table-faceted-filter";
import { DataTableToolbarActions } from "./data-table-toolbar-actions";
import { DataTableViewOptions } from "./data-table-view-options";

interface DataTableToolbarProps<TData> {
    table: Table<TData>;
}

export function DataTableToolbar<TData>({ table }: DataTableToolbarProps<TData>) {
    return (
        <div className="flex items-center justify-between">
            <div className="flex flex-1 items-center space-x-2">
                <Input
                    placeholder="Filter tasks..."
                    value={(table.getColumn("title")?.getFilterValue() as string) ?? ""}
                    onChange={(event) => table.getColumn("title")?.setFilterValue(event.target.value)}
                    className="h-8 w-full"
                />
                {table.getColumn("category") && <DataTableFacetedFilter column={table.getColumn("category")} title="Category" options={categories} />}
                <DataTableViewOptions table={table} />
                <DataTableToolbarActions />
            </div>
        </div>
    );
}
