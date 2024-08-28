import { z } from "zod";

export interface Microservice {
  id: string;
  title: string;
  description: string;
  category: string;
  name: string;
  email: string;
  date: string;
  version: string;
  dependencies: string[];
  specification: string;
}

export const microserviceSchema = z.object({
  id: z.string(),
  title: z.string(),
  description: z.string(),
  category: z.string(),
  name: z.string(),
  email: z.string(),
  date: z.string(),
  version: z.string(),
  dependencies: z.array(z.string()),
  specification: z.string(),
});

export type MicroserviceSchema = z.infer<typeof microserviceSchema>;
