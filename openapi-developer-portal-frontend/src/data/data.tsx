import {
    BackpackIcon,
    BarChartIcon,
    CountdownTimerIcon,
    EnvelopeClosedIcon,
    EyeOpenIcon,
    FileTextIcon,
    LightningBoltIcon,
    LockClosedIcon,
    PersonIcon,
    QuoteIcon,
    RocketIcon,
    Share1Icon,
    Share2Icon,
    TargetIcon,
} from "@radix-ui/react-icons";

export const categories = [
    {
        value: "Analytics",
        label: "Analytics",
        icon: BarChartIcon,
    },
    {
        value: "Application",
        label: "Application",
        icon: RocketIcon,
    },
    {
        value: "Automation",
        label: "Automation",
        icon: TargetIcon,
    },
    {
        value: "Communication",
        label: "Communication",
        icon: EnvelopeClosedIcon,
    },
    {
        value: "Connectivity",
        label: "Connectivity",
        icon: LightningBoltIcon,
    },
    {
        value: "Database",
        label: "Database",
        icon: FileTextIcon,
    },
    {
        value: "Management",
        label: "Management",
        icon: PersonIcon,
    },
    {
        value: "Marketplace",
        label: "Marketplace",
        icon: BackpackIcon,
    },
    {
        value: "Monitoring",
        label: "Monitoring",
        icon: EyeOpenIcon,
    },
    {
        value: "Security",
        label: "Security",
        icon: LockClosedIcon,
    },
    {
        value: "Subscription",
        label: "Subscription",
        icon: QuoteIcon,
    },
];

export const actions = [
    {
        value: "Export",
        label: "Export",
        toast: {
            title: "Exported To CSV.",
            description: "The microservices have been exported to CSV.",
        },
        icon: Share1Icon,
    },
    {
        value: "Share",
        label: "Share",
        toast: {
            title: "Link Copied To Clipboard.",
            description: "Send the link to your colleagues.",
        },
        icon: Share2Icon,
    },
    {
        value: "Reset",
        label: "Reset",
        toast: {
            title: "Databased Initialized.",
            description: "Microservices data has been reset.",
        },
        icon: CountdownTimerIcon,
    },
];

export const endpoints = [
    {
        name: "source",
        endpoint: "http://localhost:8080/api/get/source",
    },
];
