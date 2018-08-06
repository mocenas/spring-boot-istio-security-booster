package me.snowdrop.istio.booster.security;

import org.arquillian.cube.istio.api.IstioResource;
import org.arquillian.cube.istio.impl.IstioAssistant;
import org.arquillian.cube.openshift.impl.client.OpenShiftAssistant;
import org.arquillian.cube.openshift.impl.enricher.RouteURL;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Martin Ocenas
 */
@RunWith(Arquillian.class)
@IstioResource("classpath:gateway.yml")
public class OpenshiftIT {
    private static final String ISTIO_NAMESPACE = "istio-system";
    private static final String ISTIO_INGRESS_GATEWAY_NAME = "istio-ingressgateway";

    @RouteURL(value = ISTIO_INGRESS_GATEWAY_NAME, namespace = ISTIO_NAMESPACE)
    private URL ingressGatewayURL;

    @ArquillianResource
    private IstioAssistant istioAssistant;

    @ArquillianResource
    private OpenShiftAssistant openShiftAssistant;


    // DOES NOT WORK !!
    @Test
    public void modifyTemplateTest() throws IOException {
        List<me.snowdrop.istio.api.model.IstioResource> resource=deployRouteRule("block-greeting-service.yml");

        istioAssistant.undeployIstioResources(resource);
    }

    private List<me.snowdrop.istio.api.model.IstioResource> deployRouteRule(String routeRuleFile) throws IOException {
        return istioAssistant.deployIstioResources(
                Files.newInputStream(Paths.get("../rules/" + routeRuleFile)));
    }
}
