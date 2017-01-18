# sunflower

## feature

 - support queue. persistence, load balance

# mqtt specification (v3.1.1)

[mqtt.org](http://docs.oasis-open.org/mqtt/mqtt/v3.1.1/mqtt-v3.1.1.html)

# Introduction

## Data representations

### Bits

### Integer data values

 - 16bits
 - big-endian

### UTF-8 encoded String

 - 最初の2byteは文字列の長さを表す。つまり、最大65535bytesまで。
 - 以下のコードは含めてはいけない。serverとclientはこれらを受け取るとコネクションを切断する。
  + U+0000
  + U+D800からU+DFFF
 - 以下のコードは含めるべきではない。serverとclientはコネクションを切断する場合がある。
  + U+0001FからU+001F
  + U+007FからU+009F
 - シーケンス「0xEF 0xBB 0xBF」は「U+FEFF」と解釈する。skipしてはいけない。

# パケットフォーマット

## MQTTパケットの構造

 - Fixed header: すべてのパケットで使用する。
 - Variable header: いくつかのパケットで使用する。
 - Payload: いくつかのパケットで使用する。

## Fixed header

 - 1byte: パケットタイプ(4bit) + フラグ(4bit)
 - 2byte: 残りのデータの長さ

### パケットタイプ

 - Position: byte 1, bits 7-4

### フラグ

 - Position: byte 1, bits 3-0


 - MQTTパケットタイプにより異なる。
 - Reservedの値もきちんと定義する。
 - 正しくないフラグを受け取った場合、serverとclientはコネクションを切断する。


 - DUP: PUBLISHパケットの重複配送フラグ
 - QoS: PUBLISH Quality of Service
 - RETAIN: PUBLISH Retainフラグ

### 残りのデータの長さ

 - Position: starts at byte 2


 - 残りのデータの長さとは、パケットの残りのbytes数である。(variable headerとpayloadの合計)
 - 「残りのデータの長さ」の長さ自体は含まない。
 - 残りの長さは、可変長符号化方式を使用して符号化される。
 - 最大127までの値に対して1byte使用する。
 - 各バイトの7bitはデータの符号化に使用する。最上位1bitは、長さを表すために次のbyteを使うことを示すための「continuation bit」として使用する。
 - 「残りのデータの長さ」に使用するbyte数は最大4byte。

## Variable header

### Packet Identifier

## Payload

# MQTTパケット

## CONNECT

## CONNACK

## PUBLISH

## PUBACK

## PUBREC

## PUBREL

## PUBCOMP

## SUBSCRIBE

## SUBACK

## UNSUBSCRIBE

## UNSUBACK

## PINGREQ

## PINGRESP

## DISCONNECT
