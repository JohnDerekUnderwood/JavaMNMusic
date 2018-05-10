/*
* John Underwood
*
* The rating class.
*
* */

package com.john;

public class Rating {
    private String Venue, City;
    private int Rating;



public Rating () {

}
    public Rating(String Venue, String City, int Rating)
    {
        //Creating objects
        this.Venue = Venue;
        this.City = City;
        this.Rating = Rating;
    }
    public String getCity() {
        return City;
    }

    public String getVenue(){
        return Venue;
    }

    public int getRating() {
        return Rating;
    }
    public void setVenue(String venue) {
        this.Venue = venue;
    }
    public void setCity(String city) {
        this.City = city;
    }
    public void setRating(Integer rating) {
        this.Rating = rating;
    }
}
