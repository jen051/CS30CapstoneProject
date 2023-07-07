package backend_models;

import apprunner.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;

public class WebScraper {

    private String title;
    private String authors;
    private String rating;
    private String price;
    private String description;
    private Image cover;
    public int index;
    public String link;
    public String isbn;
    public String coverLink;
    public String publisher;

    public void get(String keywords) throws IOException, Exception, NullPointerException {
        index = 0;
        String sURL = "https://www.googleapis.com/books/v1/volumes?q=" + keywords.toLowerCase().replace(" ", "") + "&maxResults=40"; //format url
        try {
            URL url = new URL(sURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                String output;
                String totalString = "";
                while ((output = br.readLine()) != null) {
                    totalString += output;
                }

                JsonObject jsonObject = new JsonParser().parse(totalString).getAsJsonObject();
                int totalItems = jsonObject.get("totalItems").getAsInt();

                if (totalItems > 0) {
                    JsonArray items = jsonObject.getAsJsonArray("items");

                    for (int i = 0; i < items.size() && index < BackendModelSetup.searchResultBooks.length; i++) {

                        title = "";

                        JsonObject anItem = items.get(i).getAsJsonObject();
                        link = anItem.get("selfLink").getAsString();

                        JsonObject volumeInfo = anItem.getAsJsonObject("volumeInfo");
                        boolean valid = false;

                        try {
                            JsonArray industryIdentifier = volumeInfo.getAsJsonArray("industryIdentifiers");
                            for (int k = 0; k < industryIdentifier.size() && (!valid); k++) {
                                JsonObject anIdentifier = industryIdentifier.get(k).getAsJsonObject();
                                String type = anIdentifier.get("type").getAsString();
                                if (type.contains("ISBN")) {
                                    valid = true;
                                    isbn = anIdentifier.get("identifier").getAsString();
                                } else {
                                    valid = false;
                                }
                            }
                        } catch (Exception e) {
                        }

                        if (valid) {
                            title = volumeInfo.get("title").getAsString();
                            try {
                                String subtitle = volumeInfo.get("subtitle").getAsString();
                                title = title + ": " + subtitle;
                            } catch (NullPointerException e) {
                            }

                            setBookInfo();
                        }

                    }
                }
            }
        } catch (JsonSyntaxException | IOException e) {
        }
    }

    public void getDetails(int idx) throws IOException, Exception, NullPointerException {

        String sURL = BackendModelSetup.searchResultBooks[idx].getLink(); //format url
        cover = Constants.getImageUnavailableImage();

        try {
            URL url = new URL(sURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                String output;
                String totalString = "";
                while ((output = br.readLine()) != null) {
                    totalString += output;
                }

                JsonObject jsonObject = new JsonParser().parse(totalString).getAsJsonObject();

                authors = "";
                rating = "";
                price = "";
                description = "";
                coverLink = "";
                publisher = "";
                JsonObject volumeInfo = jsonObject.getAsJsonObject("volumeInfo");
                JsonObject saleInfo = jsonObject.getAsJsonObject("saleInfo");

                //get authors
                try {
                    JsonArray authorsArr = volumeInfo.get("authors").getAsJsonArray();

                    for (int k = 0; k < authorsArr.size(); k++) {
                        if (k == authorsArr.size() - 1) {
                            authors += authorsArr.get(k).getAsString();
                        } else {
                            authors = authors + authorsArr.get(k).getAsString() + ", ";
                        }
                    }
                    authors = formatStr(authors);

                } catch (NullPointerException e) {
                }

                //get rating
                try {
                    rating = volumeInfo.get("averageRating").getAsString();
                    rating = formatStr(rating);

                } catch (NullPointerException e) {
                    rating = "Unavailable";
                }

                //get description
                try {
                    description = volumeInfo.get("description").getAsString();
                    description = formatStr(description);

                } catch (NullPointerException e) {
                    description = "";
                }

                //get publisher
                try {
                    publisher = volumeInfo.get("publisher").getAsString();
                    publisher = formatStr(publisher);

                } catch (NullPointerException e) {
                    publisher = "";
                }
                //get price
                try {
                    JsonObject priceArr = saleInfo.get("listPrice").getAsJsonObject();
                    price = priceArr.get("amount").getAsString();
                    price = formatStr(price);

                } catch (NullPointerException e) {
                    price = "Unavailable";
                }

                //get cover
                try {
                    JsonObject coverArr = volumeInfo.get("imageLinks").getAsJsonObject();
                    URL coverURL;
                    try {
                        coverURL = new URL(coverArr.get("thumbnail").getAsString());
                        coverLink = coverURL.toString();
                        cover = ImageIO.read(coverURL);
                    } catch (NullPointerException e) {
                        coverURL = new URL(coverArr.get("smallThumbnail").getAsString());
                        coverLink = coverURL.toString();
                        cover = ImageIO.read(coverURL);
                    }
                } catch (NullPointerException e) {
                }
                setBookDetails(BackendModelSetup.theBookID);
            }
        } catch (JsonSyntaxException | IOException e) {
        }
    }

    private void setBookInfo() {
        if (index > 0) {
            for (int n = 0; n < index; n++) {
                if (BackendModelSetup.searchResultBooks[n].getTitle().equalsIgnoreCase(title)) {
                    break;
                } else {
                    createBook(index);
                    index++;
                    break;
                }
            }
        } else {
            createBook(index);
            index++;
        }
    }

    private void setBookDetails(int i) {
        BackendModelSetup.searchResultBooks[i].setAuthor(authors);
        BackendModelSetup.searchResultBooks[i].setDescription(description);
        BackendModelSetup.searchResultBooks[i].setRating(rating);
        BackendModelSetup.searchResultBooks[i].setPrice(price);
        BackendModelSetup.searchResultBooks[i].setCover(cover);
        BackendModelSetup.searchResultBooks[i].setCoverLink(coverLink);
        BackendModelSetup.searchResultBooks[i].setPublisher(publisher);
    }

    private void createBook(int index) {
        BackendModelSetup.searchResultBooks[index] = new Book();
        BackendModelSetup.searchResultBooks[index].setTitle(title);
        BackendModelSetup.searchResultBooks[index].setLink(link);
        BackendModelSetup.searchResultBooks[index].setISBN(isbn);
    }

    private String formatStr(String str) {
        str = str.replace("<p>", "").replace("<b>", "").replace("<r>", "").replace("<i>", "").replace("<br>", "");
        str = str.replace("</p>", "").replace("</b>", "").replace("</r>", "").replace("</i>", "").replace("</br>", "");
        return str;
    }
}
