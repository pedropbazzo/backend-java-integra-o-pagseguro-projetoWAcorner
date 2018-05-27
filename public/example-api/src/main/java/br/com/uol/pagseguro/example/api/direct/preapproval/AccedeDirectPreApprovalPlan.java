package br.com.uol.pagseguro.example.api.direct.preapproval;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.common.domain.builder.AddressBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.DocumentBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PhoneBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.SenderBuilder;
import br.com.uol.pagseguro.api.common.domain.enums.DocumentType;
import br.com.uol.pagseguro.api.common.domain.enums.State;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.direct.preapproval.DirectPreApprovalRegistrationBuilder;
import br.com.uol.pagseguro.api.direct.preapproval.RegisteredDirectPreApproval;
import br.com.uol.pagseguro.api.http.JSEHttpClient;
import br.com.uol.pagseguro.api.utils.logging.SimpleLoggerFactory;


import java.text.SimpleDateFormat;

public class AccedeDirectPreApprovalPlan {
    public static void main(String[] args){

        String sellerEmail = "your_seller_email";
        String sellerToken = "your_seller_token";

        try{

            final PagSeguro pagSeguro = PagSeguro
                    .instance(new SimpleLoggerFactory(), new JSEHttpClient(),
                            Credential.sellerCredential(sellerEmail, sellerToken), PagSeguroEnv.SANDBOX);

            // Aderindo ao plano FFAC8AE62424AC5884C90F8DAAE2F21A
            RegisteredDirectPreApproval registeredDirectPreApproval = pagSeguro.directPreApprovals().accession(
                    new DirectPreApprovalRegistrationBuilder()
                            .withPlan("FFAC8AE62424AC5884C90F8DAAE2F21A")
                            .withReference("José Comprador")
                            .withSender(new SenderBuilder()
                                            .withName("José Comprador")
                                            .withEmail("senderemail@sandbox.pagseguro.com.br")
                                            .withIp("1.1.1.1")
                                            .withHash("HASHHERE")
                                            .withPhone(new PhoneBuilder()
                                                    .withAreaCode("99")
                                                    .withNumber("99999999")
                                            )
                                            .withAddress(new AddressBuilder()
                                                    .withStreet("Av. PagSeguro")
                                                    .withNumber("9999")
                                                    .withComplement("99o andar")
                                                    .withDistrict("Jardim Internet")
                                                    .withCity("Cidade Exemplo")
                                                    .withState(State.SP)
                                                    .withCountry("BRA")
                                                    .withPostalCode("99999999")
                                            )
                                            .addDocument(new DocumentBuilder()
                                                    .withType(DocumentType.CPF)
                                                    .withValue("99999999999")
                                            )
                            )

                            .withPaymentMethod(new PreApprovalPaymentMethodBuilder()
                                    .withType("CREDITCARD")
                                    .withCreditCard(new PreApprovalCreditCardBuilder()
                                            .withToken("CREDITCARDTOKENHERE")
                                            .withHolder(new PreApprovalHolderBuilder()
                                                    .withName("JOSÉ COMPRADOR")
                                                    .withBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse("20/12/1990"))
                                                    .addDocument(new DocumentBuilder()
                                                            .withType(DocumentType.CPF)
                                                            .withValue("99999999999")
                                                    )
                                                    .withPhone(new PhoneBuilder()
                                                            .withAreaCode("99")
                                                            .withNumber("99999999")
                                                    )
                                                    .withBillingAddress(new AddressBuilder()
                                                            .withStreet("Av. PagSeguro")
                                                            .withNumber("9999")
                                                            .withComplement("99o andar")
                                                            .withDistrict("Jardim Internet")
                                                            .withCity("Cidade Exemplo")
                                                            .withState(State.SP)
                                                            .withCountry("BRA")
                                                            .withPostalCode("99999999")
                                                    )
                                            )
                                    )
                            )
            );

            System.out.println(registeredDirectPreApproval.getPreApprovalCode());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
