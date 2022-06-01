# Url-Shortener Microservice (WIP)

Operations:

## POST /

Takes a URL in post body and returns a new random alias to be used as a shortened URL.

e.g.

Post Body:
```json
{
  "url":"www.google.com"
}
```

Returns:

`200 OK`
```json


{
  "url":"www.google.com",
  "alias":"a1b2c3d4"
}
```

WIP
