package io.github.roger;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static com.sun.xml.internal.ws.api.message.Packet.Status.Request;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class OkHttpUtils {
    // 缓存客户端实例
    public static OkHttpClient client = new OkHttpClient();

    // GET 调用
    public static String getAsString(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().toString();
        }
    }

//    public static void res(ChannelHandlerContext ctx,String url) throws IOException {
//        handleResponse(ctx,OkHttpUtils.getAsString(url));
//    }



    public static void handleResponse(ChannelHandlerContext ctx, String body) throws Exception {
        FullHttpResponse response = null;

//            String value = "hello,kimmking";
//            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
//            response.headers().set("Content-Type", "application/json");
//            response.headers().setInt("Content-Length", response.content().readableBytes());


            //byte[] body = EntityUtils.toByteArray(endpointResponse.getEntity());
//            System.out.println(new String(body));
//            System.out.println(body.length);
            byte[] body1 = body.getBytes();


            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body1));

            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", body1.length);
            ctx.write(response);
            ctx.flush();

//        } catch (Exception e) {
//            e.printStackTrace();
//            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
//        } finally {
//            if (body1) {
//                if (!HttpUtil.isKeepAlive(fullRequest)) {
//                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
//                } else {
//                    //response.headers().set(CONNECTION, KEEP_ALIVE);
//                    ctx.write(response);
//                }
//            }
//            ctx.flush();
//            //ctx.close();
//        }

    }

    public static void main(String[] args) throws Exception {

        String url = " http://localhost:8088";
        String text = OkHttpUtils.getAsString(url);
        System.out.println("url: " + url + " ; response: \n" + text);

        // 清理资源
        OkHttpUtils.client = null;
    }
}