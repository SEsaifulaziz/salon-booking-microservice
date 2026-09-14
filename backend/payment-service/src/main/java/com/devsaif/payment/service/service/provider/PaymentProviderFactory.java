package com.devsaif.payment.service.service.provider;

import com.devsaif.payment.service.domain.PaymentMethod;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class PaymentProviderFactory {

    private final Map<PaymentMethod, PaymentProvider> providers;

    public PaymentProviderFactory(
            StripePaymentProvider stripePaymentProvider,
            JazzCashPaymentProvider jazzCashPaymentProvider
    ) {
        this.providers = new EnumMap<>(PaymentMethod.class);

        providers.put(
                PaymentMethod.STRIPE,
                stripePaymentProvider
        );

        providers.put(
                PaymentMethod.JAZZCASH,
                jazzCashPaymentProvider
        );
    }

    public PaymentProvider getProvider(PaymentMethod paymentMethod) {

        PaymentProvider provider = providers.get(paymentMethod);

        if (provider == null) {
            throw  new IllegalArgumentException(
                    "No payment provider configured for: " + paymentMethod
            );
        }

        return provider;
    }

}
