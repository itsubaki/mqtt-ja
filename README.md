# sunflower

## future

 - support queue.
 - persistence.
 - load balance.
 - end to end QoS
 
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
 - 以下のコードは含めてはいけない。serverとclientはこれらを受け取るとコネクションを切断する
  + U+0000
  + U+D800からU+DFFF
 - 以下のコードは含めるべきではない。serverとclientはコネクションを切断する場合がある。
  + U+0001FからU+001F
  + U+007FからU+009F
 - シーケンス「0xEF 0xBB 0xBF」は「U+FEFF」と解釈する。skipしてはいけない。
 - Non normative example: 省略

# MQTT制御パケットフォーマット

## 1MQTT制御パケットの構造

 - Fixed header: すべての制御パケットで使用する。
 - Variable header: いくつかの制御パケットで使用する。
 - Payload: いくつかの制御パケットで使用する。

## Fixed header

 - 1byte: MQTT制御パケットタイプ(4bit) + フラグ(4bit)
 - 2byte: 残りのデータの長さ

### MQTT制御パケットタイプ

 - Position: byte 1, bits 7-4


### フラグ
 
 - Position: byte 1, bits 3-0
 - MQTT制御パケットタイプにより異なる。
 - Reservedの値もきちんと定義する。
 - 正しくないフラグを受け取った場合、serverとclientはコネクションを切断する。

### 残りのデータの長さ

 - Position: starts at byte 2
 - 残りのデータの長さとは、パケットの残りのbytes数である。(variable headerとpayloadの合計)
 - 「残りのデータの長さ」の長さ自体は含まない。
