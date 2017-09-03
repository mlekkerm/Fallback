import com.sksamuel.elastic4s.{ElasticsearchClientUri, RefreshPolicy}
import com.sksamuel.elastic4s.http.HttpClient
import com.sksamuel.elastic4s.http.search.SearchResponse

object ElasticTest extends App {

  // you must import the DSL to use the syntax helpers
  import com.sksamuel.elastic4s.http.ElasticDsl._

  val client = HttpClient( ElasticsearchClientUri("10.0.0.2", 32772))

  client.execute {
    bulk(
      indexInto("myindex" / "mytype").fields("country" -> "Mongolia", "capital" -> "Ulaanbaatar"),
      indexInto("myindex" / "mytype").fields("country" -> "Namibia", "capital" -> "Windhoek")
    ).refresh(RefreshPolicy.WAIT_UNTIL)
  }.await

  val result: SearchResponse = client.execute {
    search("myindex").matchQuery("capital", "ulaanbaatar")
  }.await

  // prints out the original json
  println(result.hits.hits.head.sourceAsString)

  client.close()
}
