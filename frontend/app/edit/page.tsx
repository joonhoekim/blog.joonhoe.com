import {Card, CardContent, CardHeader} from "@/components/ui/card";
import {cn} from "@/lib/utils";

export default function App() {
  return (
      <>
        <Card className={cn(
            'm-4'
        )}>
          <CardHeader>card-header</CardHeader>
          <CardContent>

          </CardContent>
        </Card>
      </>
  );
}
