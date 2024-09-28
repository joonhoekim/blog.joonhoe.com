import { buttonVariants } from '@/components/ui/button'
import { siteConfig } from '@/config/site'
import Link from 'next/link'

export default function IndexPage() {
  return (
      <section className="container grid items-center gap-6 py-8 md:py-10">
        <div className="flex max-w-[980px] flex-col items-start gap-2">
          <h1 className="text-3xl font-extrabold leading-tight tracking-tighter md:text-4xl">
            Simple yet complete?
            <br className="hidden sm:inline" />
            but it is true.
          </h1>
          <p className="max-w-[700px] text-lg text-muted-foreground">
            Actually, I started to make this for myself.
          </p>
        </div>
        <div className="flex gap-4">
          <Link
              href={siteConfig.links.docs}
              target="_blank"
              rel="noreferrer"
              className={buttonVariants()}
          >
            Documentation
          </Link>
          <Link
              target="_blank"
              rel="noreferrer"
              href={siteConfig.links.github}
              className={buttonVariants({ variant: 'outline' })}
          >
            GitHub
          </Link>
        </div>
      </section>
  )
}
