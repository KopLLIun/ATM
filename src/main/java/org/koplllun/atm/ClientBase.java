package org.koplllun.atm;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "ClientBase")
class ClientBase {
    //@XmlElementWrapper(name = "CashCards")
    private List<CashCard> clientBase;

    public void setClientBase(List<CashCard> clientBase) {
        this.clientBase = clientBase;
    }

    @XmlElement(name = "CashCard")
    public List<CashCard> getClientBase() {
        return clientBase;
    }

    public String toString() {
        String res = "";
        for (CashCard cashCard : clientBase) {
            res += cashCard.toString();
            res += '\n';
        }
        return res;
    }
}
