import { Row } from "@tanstack/react-table";
import { CartesianGrid, Line, LineChart, ResponsiveContainer, Tooltip } from "recharts";

interface DataTableRowActionsProps<TData> {
    row: Row<TData>;
}

interface Node {
    id: string;
    name: string;
    // Add any other properties you have in your data
}

interface Link {
    source: string;
    target: string;
    // Add any other properties you have in your data
}

interface Props {
    data: {
        id: any;
        nodes: Node[];
        links: Link[];
    };
}

export function DailyUsage() {
    function data() {
        const data = [];

        for (let i = 0; i < 5; i++) {
            const average = Math.floor(Math.random() * 1000);
            const today = Math.floor(Math.random() * 1000);

            data.push({ average, today });
        }

        return data;
    }

    return (
        <ResponsiveContainer width="100%" height="100%" className=" h-full rounded-md border">
            <LineChart
                data={data()}
                margin={{
                    top: 0,
                    right: 0,
                    left: 0,
                    bottom: 0,
                }}
            >
                <Tooltip
                    content={({ active, payload }) => {
                        if (active && payload && payload.length) {
                            return (
                                <div className="rounded-lg border bg-background p-2 shadow-sm">
                                    <div className="grid grid-cols-2 gap-2">
                                        <div className="flex flex-col">
                                            <span className="text-[0.70rem] uppercase text-mutedforeground">Average</span>
                                            <span className="font-bold text-muted-foreground">{payload[0].value}</span>
                                        </div>
                                        <div className="flex flex-col">
                                            <span className="text-[0.70rem] uppercase text-muted-foreground">Today</span>
                                            <span className="font-bold">{payload[1].value}</span>
                                        </div>
                                    </div>
                                </div>
                            );
                        }
                        return null;
                    }}
                />
                <CartesianGrid strokeDasharray="3 3" />
                <Line
                    type="monotone"
                    strokeWidth={2}
                    dataKey="average"
                    stroke="#83c44c"
                    activeDot={{
                        r: 6,
                        style: { fill: "#83c44c", opacity: 0.25 },
                    }}
                />
                <Line
                    type="monotone"
                    dataKey="today"
                    strokeWidth={2}
                    stroke="#11b0dd"
                    activeDot={{
                        r: 8,
                        style: { fill: "#11b0dd", opacity: 0.75 },
                    }}
                />
            </LineChart>
        </ResponsiveContainer>
    );
}
