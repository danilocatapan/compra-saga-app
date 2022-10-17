package com.danilocatapan;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/compra-orquestrada")
public class CompraOrquestradaResource {

    @Inject
    CreditoService creditoService;

    @Inject
    PedidosService pedidosService;

    @GET
    @Path("teste")
    @Produces(MediaType.TEXT_PLAIN)
    public Response saga() {

        // credito = 100;
        Long id = 0L;

        comprar(++id, 20);
        comprar(++id, 30);
        comprar(++id, 30);
        comprar(++id, 25);

        return Response.ok().build();
    }

    private void comprar(Long id, int valor) {
        pedidosService.newPedido(id);
        try {
            creditoService.newPedidoValor(id, valor);
            System.out.println("Pedido " + id + " registrado no valor de " + valor);
        } catch (IllegalStateException e) {
            pedidosService.cancelPedido(id);
            System.err.println("Pedido " + id + " estornado no valor de " + valor);
        }
    }
}
