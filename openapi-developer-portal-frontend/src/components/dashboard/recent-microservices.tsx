import { DatabaseController } from "../../lib/api";
import { FormattedDate } from "../../lib/date";
import { Avatar, AvatarFallback, AvatarImage } from "../ui/avatar";

export function RecentMicroservices() {
    const { microservices } = DatabaseController();

    return (
        <div className="space-y-8">
            <RecentMicroserviceEntry data={microservices} />
        </div>
    );
}

export function RecentMicroserviceEntry({ data }: any) {
    return (
        <div className="space-y-8">
            <div className="space-y-4">
                <div className="space-y-4">
                    {data.map((data: any) => (
                        <div className="flex items-center">
                            <Avatar className="h-9 w-9">
                                <AvatarImage src={`https://avatar.vercel.sh/${data.name}.png`} alt="Avatar" />
                                <AvatarFallback>OM</AvatarFallback>
                            </Avatar>
                            <div className="ml-4 space-y-1">
                                <p className="text-sm font-medium leading-none">{data.title}</p>
                                <p className="text-sm text-muted-foreground">{data.version}</p>
                            </div>
                            <div className="ml-auto font-medium">
                                <FormattedDate date={data.date} />
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}
