import * as z from "zod";

import { zodResolver } from "@hookform/resolvers/zod";
import { useFieldArray, useForm } from "react-hook-form";

import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from "../ui/form";

import { DatabaseController } from "../../lib/api";
import { cn } from "../../lib/utils";
import { Button } from "../ui/button";
import { Input } from "../ui/input";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "../ui/select";
import { Textarea } from "../ui/textarea";
import { toast } from "../ui/use-toast";

import { faker } from "@faker-js/faker";
import { CaretSortIcon, CheckIcon, PaperPlaneIcon, PlusCircledIcon } from "@radix-ui/react-icons";
import Case from "case";
import { Command, CommandEmpty, CommandGroup, CommandInput, CommandItem } from "../ui/command";
import { Popover, PopoverContent, PopoverTrigger } from "../ui/popover";

const microserviceFormSchema = z.object({
    title: z.string({ required_error: "Please enter a title." }).min(2, {
        message: "Title must be at least 2 characters.",
    }),
    description: z
        .string({
            required_error: "Please enter a description.",
        })
        .max(160)
        .min(4),
    category: z.string({
        required_error: "Please select a category.",
    }),
    name: z.string({
        required_error: "Please enter a name to display.",
    }),
    email: z
        .string({
            required_error: "Please select an email to display.",
        })
        .email(),
    version: z.string(),
    dependencies: z
        .array(
            z.object({
                value: z.string(),
            })
        )
        .min(1, { message: "Please select at least one dependency." }),
    specification: z.string(),
});

type MicroserviceFormValues = z.infer<typeof microserviceFormSchema>;

const defaultValues: Partial<MicroserviceFormValues> = {
    title:
        faker.hacker.abbreviation() +
        " " +
        Case.capital(faker.hacker.adjective()) +
        " " +
        Case.capital(faker.hacker.noun()) +
        " " +
        Case.capital(faker.hacker.verb()) +
        " API",
    description: faker.hacker.phrase(),
    category: "Analytics",
    name: faker.name.fullName(),
    email: faker.internet.email(),
    version: "3.0.0",
    dependencies: [{ value: "Business Analytics Report API" }],
    specification: "editor.swagger.io",
};

export function MicroserviceForm() {
    const { post, view, microservices, titles } = DatabaseController();

    const form = useForm<MicroserviceFormValues>({
        resolver: zodResolver(microserviceFormSchema),
        defaultValues,
        mode: "onChange",
    });

    function onSubmit(data: MicroserviceFormValues) {
        console.log(data);
        toast({
            title: "You submitted the following values:",
            description: (
                <pre className="mt-2 w-[340px] rounded-md bg-slate-950 p-4">
                    <code className="text-white">{JSON.stringify(data, null, 2)}</code>
                </pre>
            ),
        });
        post(
            data.title,
            data.description,
            data.category,
            data.name,
            data.email,
            data.version,
            data.dependencies?.map((dependency) => dependency.value).join(",") ?? "",
            data.specification
        );
    }

    const { fields, append } = useFieldArray({
        name: "dependencies",
        control: form.control,
    });

    const max = 5;
    return (
        <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
                <div className="grid grid-cols-2 gap-4">
                    <FormField
                        control={form.control}
                        name="title"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel>Title</FormLabel>
                                <FormControl>
                                    <Input {...field} />
                                </FormControl>
                                <FormMessage />
                                <FormDescription>Enter a title for your microservice.</FormDescription>
                            </FormItem>
                        )}
                    />
                    <FormField
                        control={form.control}
                        name="category"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel>Category</FormLabel>
                                <Select onValueChange={field.onChange} defaultValue={field.value}>
                                    <FormControl>
                                        <SelectTrigger>
                                            <SelectValue placeholder="Select a category" />
                                        </SelectTrigger>
                                    </FormControl>
                                    <SelectContent>
                                        <SelectItem value="Analytics">Analytics</SelectItem>
                                        <SelectItem value="Application">Application</SelectItem>
                                        <SelectItem value="Automation">Automation</SelectItem>
                                        <SelectItem value="Communication">Communication</SelectItem>
                                        <SelectItem value="Connectivity">Connectivity</SelectItem>
                                        <SelectItem value="Database">Database</SelectItem>
                                        <SelectItem value="Management">Management</SelectItem>
                                        <SelectItem value="Marketplace">Marketplace</SelectItem>
                                        <SelectItem value="Monitoring">Monitoring</SelectItem>
                                        <SelectItem value="Security">Security</SelectItem>
                                        <SelectItem value="Subscription">Subscription</SelectItem>
                                    </SelectContent>
                                </Select>
                                <FormDescription>Select a category for your microservice.</FormDescription>
                            </FormItem>
                        )}
                    />
                </div>
                <FormField
                    control={form.control}
                    name="description"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Description</FormLabel>
                            <FormControl>
                                <Textarea {...field} className="max-h-[200px]" />
                            </FormControl>
                            <FormMessage />
                            <FormDescription>
                                Enter a description for your microservice. Try to be as descriptive as possible. Include the purpose, functionality, and any other relevant
                                information.
                            </FormDescription>
                        </FormItem>
                    )}
                />
                <div className="grid grid-cols-2 gap-4">
                    <FormField
                        control={form.control}
                        name="name"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel>Name</FormLabel>
                                <FormControl>
                                    <Input {...field} />
                                </FormControl>
                                <FormMessage />
                                <FormDescription>Enter a the name of the person who created the microservice.</FormDescription>
                            </FormItem>
                        )}
                    />
                    <FormField
                        control={form.control}
                        name="email"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel>Email</FormLabel>
                                <FormControl>
                                    <Input {...field} />
                                </FormControl>
                                <FormMessage />
                                <FormDescription>Enter a the email of the person who created the microservice.</FormDescription>
                            </FormItem>
                        )}
                    />
                </div>
                <div className="grid grid-cols-2 gap-4">
                    <FormField
                        control={form.control}
                        name="version"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel>Version</FormLabel>
                                <FormControl>
                                    <Select onValueChange={field.onChange} defaultValue={field.value}>
                                        <FormControl>
                                            <SelectTrigger>
                                                <SelectValue placeholder="Select a version" />
                                            </SelectTrigger>
                                        </FormControl>
                                        <SelectContent>
                                            <SelectItem value="3.0.0">3.0.0</SelectItem>
                                            <SelectItem value="3.0.1">3.0.1</SelectItem>
                                            <SelectItem value="3.0.2">3.0.2</SelectItem>
                                            <SelectItem value="3.0.3">3.0.3</SelectItem>
                                        </SelectContent>
                                    </Select>
                                </FormControl>
                                <FormMessage />
                                <FormDescription>Select an OpenAPI version for your microservice.</FormDescription>
                            </FormItem>
                        )}
                    />
                    <FormField
                        control={form.control}
                        name="specification"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel>Specification</FormLabel>
                                <FormControl>
                                    <Input {...field} />
                                </FormControl>
                                <FormMessage />
                                <FormDescription>Enter the specification url of the microservice.</FormDescription>
                            </FormItem>
                        )}
                    />
                </div>
                <div>
                    {fields.map((field, index) => (
                        <FormField
                            control={form.control}
                            key={field.id}
                            name={`dependencies.${index}.value`}
                            render={({ field }) => (
                                <FormItem className="flex flex-col">
                                    <FormLabel className={cn(index !== 0 && "sr-only")}>Dependencies </FormLabel>
                                    <FormDescription className={cn(index !== 0 && "sr-only")}>Select a dependency for your microservice.</FormDescription>
                                    <Popover>
                                        <PopoverTrigger asChild>
                                            <FormControl>
                                                <Button
                                                    variant="outline"
                                                    role="combobox"
                                                    className={cn("justify-between", !field.value && "text-muted-foreground")}
                                                    defaultValue={field.value}
                                                >
                                                    {field.value ? field.value : "Select dependency"}
                                                    <CaretSortIcon className="ml-2 h-4 w-4 shrink-0 opacity-50" />
                                                </Button>
                                            </FormControl>
                                        </PopoverTrigger>
                                        <PopoverContent side="bottom" sticky="always" className="max-h-[250px] overflow-y-auto p-0 width-[300px] w-[551px]">
                                            <Command>
                                                <CommandInput placeholder="Search framework..." className="h-9" />
                                                <CommandEmpty>Nothing found.</CommandEmpty>
                                                <CommandGroup>
                                                    {titles().map((title) => (
                                                        <CommandItem
                                                            value={title}
                                                            key={title}
                                                            onSelect={() => {
                                                                field.onChange(title);
                                                                document.dispatchEvent(new KeyboardEvent("keydown", { key: "Escape" }));
                                                            }}
                                                        >
                                                            {title}
                                                            <CheckIcon className={cn("ml-auto h-4 w-4", title === field.value?.toString() ? "opacity-100" : "opacity-0")} />
                                                        </CommandItem>
                                                    ))}
                                                </CommandGroup>
                                            </Command>
                                        </PopoverContent>
                                    </Popover>
                                </FormItem>
                            )}
                        />
                    ))}
                    <div className="grid grid-cols-1 gap-4">
                        <Button type="button" variant="secondary" size="sm" className="w-full mt-2" onClick={() => append({ value: "" })} disabled={fields.length >= max}>
                            <PlusCircledIcon className="ml-2 h-4 w-4" />
                        </Button>
                    </div>

                    <Button type="submit" disabled={!form.formState.isValid} className="w-full mt-4">
                        Submit <PaperPlaneIcon className="ml-2 h-4 w-4" />
                    </Button>
                </div>
            </form>
        </Form>
    );
}
