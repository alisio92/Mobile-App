package be.nmct.howest.week6horoscoop;

public class Data {

	public enum Horoscoop {
		WATERMAN("Waterman", "21 januari", "20 februari"), VISSEN("Vissen",
				"21 februari", "20 maart"), RAM("Ram", "21 maart", "20 april"), STIER(
				"Stier", "21 april", "20 mei"), TWEELING("Tweeling", "21 mei",
				"20 juni"), KREEFT("Kreeft", "21 juni", "22 juli"), LEEUW(
				"Leeuw", "23 juli", "22 augustus"), MAAGD("Maagd",
				"23 augustus", "22 september"), WEEGSCHAAL("Weegschaal",
				"23 september", "22 oktober"), SCHORPIOEN("Schorpioen",
				"23 oktober", "22 november"), BOOGSCHUTTER("Boogschutter",
				"23 november", "21 december"), STEENBOK("Steenbok",
				"22 december", "20 januari");

		private String naamHoroscoop;
		private String beginDatum;
		private String eindDatum;

		Horoscoop(String naamHoroscoop, String begindatum, String einddatum) {
			this.naamHoroscoop = naamHoroscoop;
			this.beginDatum = begindatum;
			this.eindDatum = einddatum;
		}

		public String getNaamHoroscoop() {
			return naamHoroscoop;
		}

		public String getBeginDatum() {
			return beginDatum;
		}

		public String getEindDatum() {
			return eindDatum;
		}

	}
}
