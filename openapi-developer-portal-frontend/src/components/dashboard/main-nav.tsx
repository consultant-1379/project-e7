import { cn } from "../../lib/utils";

export function MainNav({ className, ...props }: React.HTMLAttributes<HTMLElement>) {
    return (
        <nav className={cn("flex items-center space-x-4 lg:space-x-6", className)} {...props}>
            <a href="/" className="text-sm font-medium transition-colors hover:text-primary">
                Open API Developer Portal
            </a>
            <a href="http://localhost:8080/swagger-ui/index.html" target="_blank" className="text-sm font-medium text-muted-foreground transition-colors hover:text-primary" rel="noreferrer">
                Swagger
            </a>
            <a href="https://iot.developer.ericsson.com/en/learn/api-documentation/iota-rest-api" target="_blank" className="text-sm font-medium text-muted-foreground transition-colors hover:text-primary" rel="noreferrer">
                Ericsson API
            </a>
            <a href="https://gerrit.ericsson.se/#/admin/projects/OSS/ENM-Parent/SQ-Gate/com.ericsson.graduates/project-e7" target="_blank" className="text-sm font-medium text-muted-foreground transition-colors hover:text-primary" rel="noreferrer">
                Gerrit
            </a>
        </nav>
    );
}
