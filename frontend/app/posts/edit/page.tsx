"use client";

import { z } from "zod";
import {FormEvent, useState} from "react";
import { Button } from "@/components/ui/button"
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form"
import { Input } from "@/components/ui/input"
import {error} from "lib0";
import {zodResolver} from "@hookform/resolvers/zod";
import {useForm} from "react-hook-form";
import {Textarea} from "@/components/ui/textarea";

//use zod for validation
const formSchema = z.object({
  title: z.string().min(2).max(50),
  content: z.string().min(10),
})

export default function EditPage() {
  //React Hook Form 을 쓰는 경우 useState 로 상태를 만들 필요 없음

  // 1. Define your form.
  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      title: "",
      content: "",
    },
  })

  // 2. Define a submit handler.
  async function onSubmit(values: z.infer<typeof formSchema>) {
    // Do something with the form values.
    // ✅ This will be type-safe and validated.

    console.log(values)
    try {
      const response = await fetch('/api/posts', {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(values),
      });

      if (response.ok) {
        alert('POST Request successfully processed');
        form.reset();
      } else {
        alert('POST Request failed');
      }
    } catch (error) {
      console.error(error);
      alert('An error occurred while processing the request');
    }

  }
  
  return (
      <>
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
            <FormField
                control={form.control}
                name="title"
                render={({ field }) => (
                    <FormItem>
                      <FormLabel>Title</FormLabel>
                      <FormControl>
                        <Input placeholder="Title here" {...field} />
                      </FormControl>
                      <FormDescription>
                        Title will be the node name
                      </FormDescription>
                      <FormMessage />
                    </FormItem>
                )}
            />
            <FormField
                control={form.control}
                name="content"
                render={({ field }) => (
                    <FormItem>
                      <FormLabel>Content</FormLabel>
                      <FormControl>
                        <Textarea placeholder="Contents here" {...field} />
                      </FormControl>
                      <FormDescription>
                        Enter some content!
                      </FormDescription>
                      <FormMessage />
                    </FormItem>
                )}
            />
            <Button type="submit">Submit</Button>
          </form>
        </Form>
      </>
  )
}
