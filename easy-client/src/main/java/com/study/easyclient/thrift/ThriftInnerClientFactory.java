package com.study.easyclient.thrift;

import com.facebook.nifty.client.FramedClientConnector;
import com.facebook.nifty.client.NiftyClient;
import com.facebook.nifty.duplex.TDuplexProtocolFactory;
import com.study.easyclient.core.InnerClient;
import com.study.easyclient.core.InnerClientFactory;
import io.airlift.units.Duration;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;


/**
 * @author jixu
 */
public class ThriftInnerClientFactory implements InnerClientFactory {
    private static final Logger logger = LoggerFactory.getLogger(ThriftInnerClientFactory.class);

    private static final int DEFAULT_MAX_FRAME = 16777216; // 16M

    private final NiftyClient niftyClient;
    private final Duration timeout;
    private final Class<? extends TServiceClient> clientClass;
    private final Constructor<? extends TServiceClient> constructor;
    private final int maxFrame;


    public ThriftInnerClientFactory(NiftyClient niftyClient, long timeout, TimeUnit timeUnit, Class<? extends TServiceClient> clientClass) {
        this.niftyClient = niftyClient;
        this.timeout = new Duration(timeout, timeUnit);
        this.clientClass = clientClass;
        this.maxFrame = DEFAULT_MAX_FRAME;
        try {
            this.constructor = clientClass.getConstructor(TProtocol.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public InnerClient getInnerClient(InetSocketAddress address) {
        try {
            FramedClientConnector connector = new FramedClientConnector(address,
                    TDuplexProtocolFactory.fromSingleFactory(new TCompactProtocol.Factory()));
            TTransport transport = niftyClient.connectSync(clientClass, connector, timeout, timeout, timeout, timeout, maxFrame);
            TProtocol protocol = new TCompactProtocol(transport);
            return new ThriftInnerClient<>(constructor.newInstance(protocol), transport, address);
        } catch (Exception e) {
            logger.error("getInnerClient failed", e);
            return null;
        }
    }

}
