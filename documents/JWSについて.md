# JWSについて

## 生成手順
1. Headerを作る
```JSON
{
  "alg": "HA256",
  "typ": "JWT"
}
```

2. Payloadを作る
  - ここにユーザ情報を入れる
```JSON
{
  "sub": "1234567890",
  "name": "Alice",
  "admin": true
}
```

3. HeaderとPayloadをBase64URLエンコード

4. header.payloadを作る

5. Signature(署名)を生成する
  - header.payloadに対して署名アルゴリズムを適用し、Signatureを作成する
```Plain text
HMACSHA256 (
    secret,
    header.payload
)
```

6. SignatureをBase64URLエンコード

7. Header, Payload, Signatureを連結
