# p-simulator

- running api
```
cd ./api
mvn compile quarkus:dev
```

- confirm api by curl
```
$ curl -X POST -H 'Content-Type: application/json' -d '
{
  "rotation_rate_per_1000yen": 20,
  "change_rate": 4,
  "ball_reduction_rate": 0
}' localhost:8080/v1/symphogear/balance/1
```
- confirm api by .http file (using IntelliJ IDEA)
    - install .http plugin (https://pleiades.io/help/idea/http-client-in-product-code-editor.html)
    - open [sample-curl.http](https://github.com/Takaichi00/p-simulator/blob/main/api/http/sample-curl.http)
    - execute "Run localhost:8080"
