package Card;
 
public class CardEstatico
{
        String urlImageReal, urlImagePadr;
        int ID;
        public CardEstatico(String urlR, String urlP, int id)
        {
            this.ID = id;
            this.urlImagePadr = urlP;
            this.urlImageReal = urlR;
        }
        public String getUrlImageReal()
        {
            return urlImageReal;
        }

        public String getUrlImagePadr()
        {
            return urlImagePadr;
        }

        public int getID()
        {
            return ID;
        }
}