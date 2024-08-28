import { DataTable } from "./components/table/data-table";
import { Columns } from "./components/table/data-table-columns";
import { DatabaseController } from "./lib/api";

export default function Table() {
    const { microservices } = DatabaseController();

    console.info("microservices", microservices);

    return <DataTable data={microservices} columns={Columns} />;
}
