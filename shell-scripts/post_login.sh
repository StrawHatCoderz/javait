curl -X GET \
  "http://localhost:8000/api/post-login?code=$1" \
  -c cookie.txt