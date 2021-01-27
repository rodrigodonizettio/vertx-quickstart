package main.br.com.oliveira.codec;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import org.apache.commons.codec.binary.Base64;

public class MyNameCodec implements MessageCodec {
  private String name;
  private String decodedName;
  private String encodedName;

  public MyNameCodec() { }

  public MyNameCodec(String name) {
    this.name = name;
    this.encodedName = Base64.encodeBase64String(name.getBytes());
    this.decodedName = new String(Base64.decodeBase64(encodedName));
  }

  public String getName() {
    return name;
  }

  public String getEncodedName() {
    return encodedName;
  }

  public String getDecodedName() {
    return decodedName;
  }

  @Override
  public void encodeToWire(Buffer buffer, Object o) {

  }

  @Override
  public Object decodeFromWire(int pos, Buffer buffer) {
    return null;
  }

  @Override
  public Object transform(Object o) {
    return null;
  }

  @Override
  public String name() {
    return MyNameCodec.class.getName();
  }

  @Override
  public byte systemCodecID() {
    return 0;
  }
}
