package com.api.dscommerce.entities;

public enum OrderStatus {
    /*
    * Aguardando pagamento
     */
    WAITING_PAIMENT,
    /*
    * Pago
     */
    PAID,
    /*
    * Enviado
     */
    SHIPPED,
    /*
    * Entregue
     */
    DELIVERED,
    /*
    * Cancelado
     */
    CANCELED;
}
