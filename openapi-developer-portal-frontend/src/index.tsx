import * as React from "react";
import * as ReactDOM from "react-dom";

import { RouterProvider, createBrowserRouter } from "react-router-dom/dist";
import App from "./app";
import "./styles/globals.css";

const router = createBrowserRouter([
    {
        path: "/",
        element: <App />,
    },
]);

ReactDOM.render(
    <React.StrictMode>
        <RouterProvider router={router} />
    </React.StrictMode>,
    document.getElementById("root")
);
