package cz.tul.vvoleman.app.address;

/**
 * @author vvoleman
 */

public class Address {

    private final int id;
    private final String streetName;
    private final int houseNumber;
    private final int additionalNumber;

    private final int psc;
    private final String municipalityName;
    private final String districtName;

    /**
     * Constructor of Address
     * Only accessible via library-method
     * @param id ID
     * @param streetName street name
     * @param houseNumber house number
     * @param additionalNumber additional number
     * @param psc psc
     * @param municipalityName municipality name
     * @param districtName district name
     */
    public Address(int id, String streetName, int houseNumber, int additionalNumber, int psc, String municipalityName, String districtName) {
        this.id = id;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.additionalNumber = additionalNumber;
        this.psc = psc;
        this.municipalityName = municipalityName;
        this.districtName = districtName;
    }

    /**
     * Returns ID of address
     * @return id
     */
    public int getId(){
        return id;
    }

    /**
     * Returns PSC in numeric format
     * @return psc
     */
    public int getPsc() {
        return psc;
    }

    /**
     * Returns municipality name
     * @return municipality name
     */
    public String getMunicipalityName() {
        return municipalityName;
    }

    /**
     * Returns district name
     * @return district name
     */
    public String getDistrictName() {
        return districtName;
    }

    /**
     * Returns street address
     * <b>Note:</b> if there is not street name, it will be municipality name + house number
     * @return street address
     */
    public String getStreet(){
        StringBuilder sb = new StringBuilder();

        //Street does not exists
        if(streetName == null){
            sb.append(municipalityName).append(" ").append(houseNumber);
        }else{
            sb.append(streetName).append(" ").append(houseNumber);
            if(additionalNumber != 0){
                String parts = (""+psc).substring(0);
                sb.append("/").append(additionalNumber);
            }
        }

        return sb.toString();
    }

    /**
     * Returns full address
     * @return address
     */
    public String getFullAddress(){
        return getStreet() + ", " + getPrettyPsc() + " " + districtName;
    }

    /**
     * Returns pretty PSC in format "xxx xx"
     * @return psc
     */
    public String getPrettyPsc(){
        String temp = Integer.toString(psc);
        String[] parts = {temp.substring(0,3),temp.substring(3)};

        return parts[0]+" "+parts[1];
    }
}
