# MQTT Version 3.1.1

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

### Fixed

### Variable

#### Keep Alive

 - KeepAliveは秒単位の時間間隔である。
  + 16bitで表される。
  + クライアントがある制御パケットの送信した後、次の制御パケットを送信するまでに許容できる最大時間間隔である。
  + クライアントは、制御パケットの送信間隔がKeepAliveの値を超えないようにする必要がある。
  + 特に制御パケットを送信する必要がない場合は、クライアントはPINGREQパケットを送信しなければならない。
  + クライアントがPINGREQを送信した後、適切な時間内にPINGRESPを受信できなかった場合、サーバとの接続を切断すべきである。
  + クライアントは、ネットワークとサーバが動作していることを確認するために、KeepAlive値に関係なくいつでもPINGREQを送信してよい。
  + KeepAlive値が0でなく、サーバがKeepAlive値の1.5倍以内にクライアントからの制御パケットを受信しない場合、クライアントとの接続を切断しなければならない。
 - KeepAlive値0の場合、KeepAliveのメカニズムをオフにしなければならない。
  + オフの場合、サーバは非アクティブなクライアントを切断する必要はない。
 - サーバはクライアントのKeepAlive値に関わらず、いつでも非アクティブまたは非応答であると判断されたクライアントを切断しても良い。
 - KeepAlive値は、アプリケーションによって様々である。通常は数分。最大値は18h12m15s。

## CONNACK

## PUBLISH

### Fixed

#### DUP

 - Position: byte 1, bit 3
 - DUP
  + 0: このPUBLISHパケットは、serverまたはclientが最初に送信したものである。
  + 1: このPUBLISHパケットは、再送信されたものである。
 - serverまたはclientがPUBLISHパケットを再送信する場合はかならず1にしなければいけない。
 - QoS0のメッセージの場合は、必ず0にしなければならない。
 - serverは、受信したPUBLISHパケットのDUPフラグの値を、subscriberに送信する時に伝搬しない。
 - 送信するPUBLISHパケットのDUPフラグの値は、受信したPUBLISHパケットのDUPフラグの値とは独立して設定される。
 - DUPフラグの値は、PUBLISHパケットが再送信されたものかどうかによってのみ決定されなければならない。

#### QoS

 - Position: byte 1, bit 2-1
 - メッセージ配信の保証レベルを表す。

#### RETAIN

### Variable

### Payload

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

# Operational behavior

## Storing state

## QoS levels and protocol flows

## Message delivery retry

## Message receipt

## Message ordering

 - クライアントはプロトコルフローを実装する際、次のルールに従わなければならない。
  + QoS1または2の場合、PUBLISHパケットを再送信する際は、元のPUBLISHパケットの順序で再送しなければならない。
  + QoS1の場合、PUBLISHパケットを受け取った順番で、PUBACKパケットを送信しなければならない。
  + QoS2の場合、PUBLISHパケットを受け取った順番で、PUBRECパケットを送信しなければならない。
  + QoS2の場合、PUBRECパケットを受け取った順番で、PUBRELパケットを送信しなければならない。
 - サーバは、デフォルトではTopic単位で順序が保証されていなければならない。順序が保証されない機能をオプションとして提供しても良い。
 - PUBLISHパケットは(TopicおよびQoSが同じ)サブスクライバに対して、受信した順番で送信しなければならない。


## Topic Names and Topic Filters

## Handling erros
