import React from "react";
import { CommandMenu } from "./components/menu/command-menu";
import { Menu } from "./components/menu/menu";
import Table from "./table";

export default function App() {
    React.useEffect(() => {
        document.title = "Ericsson Developer Portal";
    }, []);

    return (
        <>
            <header className="supports-backdrop-blur:bg-background/60 sticky top-0 z-50 w-full border-b bg-background/95 backdrop-blur">
                <div className="px-8 flex h-14 items-center">
                    <div className="flex items-center space-x-4">
                        <span className="font-bold text-sm">OpenAPI Developer Portal</span>
                        <Menu />
                    </div>
                    <div className="flex flex-1 items-center justify-between space-x-2 md:justify-end">
                        <div className="w-full flex-1 md:w-auto md:flex-none">
                            <CommandMenu />
                        </div>
                    </div>
                </div>
            </header>
            <div className=" flex-1 space-y-4 p-8 pt-6">
                <Table />
            </div>
        </>
    );
}
