public class Kondition {
    public String art;
    VertragsLaufZeit vertragsLaufZeit;
    VerlaengerungsFrist verlaengerungsFrist;

    public Kondition(String art, VertragsLaufZeit vertragsLaufZeit, VerlaengerungsFrist verlaengerungsFrist) {
        this.art = art;
        this.vertragsLaufZeit = vertragsLaufZeit;
        this.verlaengerungsFrist = verlaengerungsFrist;
    }
}
