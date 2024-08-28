export function FormattedDate(date: any) {
    const input = new Date(date.date);

    const day = input.getUTCDate().toString().padStart(2, "0");

    const month = (input.getUTCMonth() + 1).toString().padStart(2, "0");

    const year = input.getUTCFullYear();

    const output = `${day}/${month}/${year}`;

    return <span>{output}</span>;
}
