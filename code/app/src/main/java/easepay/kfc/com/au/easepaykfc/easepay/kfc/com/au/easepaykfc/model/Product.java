package easepay.kfc.com.au.easepaykfc.easepay.kfc.com.au.easepaykfc.model;

/** @pdOid 84e9b635-fcd7-47e0-865f-9dec178f4422 */
public class Product extends Item {
    /** @pdOid beea30d8-41ce-473d-a556-41c4b2b8ba35 */
    private String name;
    /** @pdOid 75097d62-3c6c-464a-9b57-a2abeb591925 */
    private String description;
    /** @pdOid cd7b3548-671c-48ac-8f60-d7f4dc31d093 */
    private double price;
    /** @pdOid dbe866b2-8dbf-47e5-992b-84bf8678c22e */
    private String picture;

    /** @pdOid 46344590-df1e-4551-ad6c-06d29ded2512 */
    public String getName() {
        return name;
    }

    /** @param newName
     * @pdOid 24e7c104-04ad-43b6-92fe-d953e052aa2d */
    public void setName(String newName) {
        name = newName;
    }

    /** @pdOid c011bc71-7096-4cd7-af60-8a1071b152c8 */
    public String getDescription() {
        return description;
    }

    /** @param newDescription
     * @pdOid 6abc9d14-37e8-4030-850d-0bf189ff1cef */
    public void setDescription(String newDescription) {
        description = newDescription;
    }

    /** @pdOid 5dac81b7-4a66-45a9-b08d-a4d12ffccf30 */
    public double getPrice() {
        return price;
    }

    /** @param newPrice
     * @pdOid c9ba0be4-980d-4c41-acee-128d4d17a555 */
    public void setPrice(double newPrice) {
        price = newPrice;
    }

    /** @pdOid 06e4c97c-4642-46c7-8224-a17ff2b40bdb */
    public String getPicture() {
        return picture;
    }

    /** @param newPicture
     * @pdOid f02659c4-96ea-4671-b53f-0aeee6fc9dbb */
    public void setPicture(String newPicture) {
        picture = newPicture;
    }

}