# kotlin-lambda-notes-service
Example of a simple service written in Kotlin and deployable to lambda.

This service has the following endpoints:
```
curl --request GET \
  --url http://localhost:4567/notes
  
curl --request POST \
  --url http://localhost:4567/note \
  --header 'content-type: application/json' \
  --data '{
	"title": "My First Note"
}'

curl --request GET \
  --url http://localhost:4567/note/{oid}
  
  
curl --request PUT \
  --url http://localhost:4567/note/{oid} \
  --header 'content-type: application/json' \
  --data '{
	"content": "Hello World!"
}'

curl --request DELETE \
  --url http://localhost:4567/note/{oid}

  ```

Extended example of: https://github.com/hshar7/kotlin_lambda 


For MongoDB Connection, edit Constants.kt with your connecion info. A good free service to test in Lambda: https://mlab.com
