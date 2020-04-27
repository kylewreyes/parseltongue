package edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.metrics;

import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.graph.PageRank;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue.RankEdge;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue.RankGraph;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.parseltongue.RankVertex;
import edu.brown.cs.dtoth1_kreyes7_nyoung10_spate116_1.parseltongue.pdf_parser.Snippet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class KeywordExtractorTest {
  Map<String, Double> doc1;
  Map<String, Double> doc2;
  Map<String, Double> doc3;
  Map<String, Double> doc4;
  List<Map<String, Double>> testList;
  @Before
  public void setup() {
    doc1 = new HashMap<>();
    doc2 = new HashMap<>();
    doc3 = new HashMap<>();
    doc4 = new HashMap<>();
    testList = new ArrayList<>();

    doc1.put("1", 0.4);
    doc2.put("1", 0.4);
    doc3.put("1", 0.4);
    doc4.put("1", 0.4);

    doc1.put("2", 0.2);
    doc2.put("2", 0.3);
    doc3.put("2", 0.1);

    doc1.put("3", 0.01);
    doc2.put("3", 0.01);

    doc1.put("6", 0.1);
    doc2.put("6", 0.1);

    doc3.put("4", 0.01);
    doc4.put("4", 0.01);

    doc1.put("5", 1.0);
  }
  @After
  public void tearDown() {
    doc1 = null;
    doc2 = null;
    doc3 = null;
    doc4 = null;
  }

  @Test
  public void basicKeywordTest() {
    testList.add(doc1);
    testList.add(doc2);
    testList.add(doc3);
    testList.add(doc4);
    Map<String, Double> res = KeywordExtractor.extractKeywords(Arrays.asList("1", "3"), testList);
    assertTrue(res.containsKey("3"));
    //assertTrue(res.containsKey("2"));
    assertFalse(res.containsKey("1"));
    assertFalse(res.containsKey("6"));
  }

  @Test
  public void uruguayTest() {
    Snippet s1 = new Snippet(" Uruguay was known after the Second World\n" +
            " War as the Switzerland of Latin America. Its\n" +
            " population enjoyed a high standard of living and\n" +
            " democracy functioned there better than in many\n" +
            " other Latin American countries. There has been a\n" +
            " change in this situation since the beginning of the\n" +
            " 1950s. Uruguay was confronted with serious\n" +
            " economic problems, a reduction in prosperity,\n" +
            " political tensions and, since 1968, with a conside-\n" +
            " rable emigration. This development of underdeve-\n" +
            " lopment' will form the central theme of the present\n" +
            " article, taking the dependencia theory as an\n" +
            " interpretation context, although in a critical man-\n" +
            " ner");
    Snippet s2 = new Snippet(" A very brief description will first be given of the\n" +
            " rise of Uruguay as a welfare state, after which the\n" +
            " weak economic basis of the country's prosperity\n" +
            " and its economic, social, political and demogra-\n" +
            " phic decline and their underlying causes will be\n" +
            " examined in greater detail. The article concludes\n" +
            " with a postscript in which the developments\n" +
            " described are viewed against the background of\n" +
            " the dependencia' the");
    Snippet s3 = new Snippet(" Uruguay was for a long time an area of little\n" +
            " significance. After the discovery of America the\n" +
            " Spaniards quickly found that Uruguay did not\n" +
            " grow any important spices or contain valuable\n" +
            " minerals which could be easily exploited. The\n" +
            " country also proved to be practically uninhabited,\n" +
            " which meant that there were no large groups of\n" +
            " Indians whose labour could be used or who could\n" +
            " pay taxes. The Spaniards did ferry horses and\n" +
            " cattle over from Buenos Aires in 1603. These\n" +
            " animals rapidly multiplied and were hunted occa-\n" +
            " sionally.\n");
    Snippet s4 = new Snippet(" Not until the 18th century, when the trading\n" +
            " prospects in hides and dried meat became rather\n" +
            " more lucrative, were permanent settlements creat-\n" +
            " ed, which served mainly as trading posts and\n" +
            " formed the permanent headquarters from which\n" +
            " the 'gauchos' hunted the cattle. At that time the\n" +
            " Spanish crown also granted rights on the land,\n" +
            " thus laying the foundation for the great 'estan-\n" +
            " cias'. After that the large landed estates began\n" +
            " their expansion northwards from the southern\n" +
            " coastal region and the Uruguay river. Extensive\n" +
            " cattle and sheep farming remained the only\n" +
            " activity of any significance. The population conse-\n" +
            " quently remained very sparse. When Uruguay\n" +
            " became an independent state in 1 828 through the\n" +
            " intervention of the British and, as such, came to\n" +
            " function as a buffer between Argentina and\n" +
            " Brazil, the country had no more than 60 to\n" +
            " 70,000 inhabitants, of whom some 15,000 lived\n" +
            " in Montevideo.\n");
    Snippet s5 = new Snippet(" After independence important changes took\n" +
            " place in the economic sphere as a result of the\n" +
            " development from the middle of the last century\n" +
            " of an export-oriented, capitalistically organised,\n" +
            " livestock farming economy. Besides the Urugua-\n" +
            " yans themselves, it was mainly the British who\n" +
            " recognized the favourable conditions for the\n" +
            " practice of cattle and sheep farming and began\n" +
            " to encourage its development through the supply\n" +
            " of high quality breeding animals and various\n" +
            " other assets (such as barbed wire), the establish-\n" +
            " ment of a meat extract plant at Fray Bent");
    Snippet s6 = new Snippet("os, the\n" +
            " construction of railways, the introduction of refri-\n" +
            " gerated ships and the building of a modern\n" +
            " abattoir with ancillary processing plants (frigorífi-\n" +
            " co). At the beginning of the present century the\n" +
            " Americans also provided capital for the construc-\n" +
            " tion of two frigoríficos' a' Montevideo. As a\n" +
            " result of all this the cattle and sheep population\n" +
            " expanded rapidly and a further occupation of the\n" +
            " terrritory took place. The greater part of the land\n" +
            " came into the hands of large landowners, who\n" +
            " continued to exploit it in an extensive manner, so\n" +
            " that the yields per hectare remained low.");
    Snippet s7 = new Snippet(" Only 1 2% of the agricultural land was ultimate-\n" +
            " ly used for arable farming, which mainly served\n" +
            " for national self-sufficiency and largely deve-\n" +
            " loped during the present century, when the\n" +
            " economic depression and the war led people to\n" +
            " realise that the lack of attention to arable farming\n" +
            " had resulted in an extreme dependency. The\n" +
            " greater part of the arable farming is practised in\n" +
            " the southern and western part of the country,\n" +
            " which is the most suited physically to the purpose\n" +
            " and has the largest concentration of population\n" +
            " (map 1).\n");
    Snippet s8 = new Snippet(" The dependency on imported industrial\n" +
            " products was also exposed in the present century\n" +
            " and the Uruguay an government has consequently\n" +
            " strived since the First World War to expand\n" +
            " manufacturing industry by such means as protec-\n" +
            " tion and the setting up of government-owned\n" +
            " factories. The need to provide, additional employ-\n" +
            " ment in Montevideo was a further important\n" +
            " motive here. By 1961, 27% of the economically\n" +
            " active population was employed in the secondary\n" +
            " sector and the contribution of manufacturing\n" +
            " industry to the gross domestic product was 28% in\n" +
            " 1955. The greater part of the industry was service\n" +
            " industry at the national level. Uruguay was by\n" +
            " then itself already producing many consumer\n" +
            " goods in common use. The majority of factories,\n" +
            " with the exception of the frigoríficos, operated\n" +
            " with Uruguayan capital");
    Snippet s9 = new Snippet(" The strong emphasis on extensive livestock\n" +
            " farming had important consequences for the size\n" +
            " and distribution of the population. Although the\n" +
            " rise of the export-oriented livestock farming econ-\n" +
            " omy did make possible the immigration of\n" +
            " 648,000 persons during the period 1836-1926,\n" +
            " this immigration was not very large by compari-\n" +
            " son with the immigration into the neighbouring\n" +
            " counties of Brazil and Argentina. Uruguay, which\n" +
            " had only 229,000 inhabitants in I860, still had no\n" +
            " more than 505,000 in 1882, only 1.14 million in\n" +
            " 1910 and 1.9 million in 1930");
    Snippet s10 = new Snippet(" The majority of immigrants did not go to the\n" +
            " countryside, because the extensive sheep and\n" +
            " cattle ranches there offered little employment.\n" +
            " Many found a living in the secondary and tertiary\n" +
            " sectors in the city. The countryside remained very\n" +
            " thinly populated and one of the direct conse-\n" +
            " quences of this was that the growth of the urban\n" +
            " centres was severely restricted, since they could\n" +
            " perform only a modest service function");
    Snippet s11 = new Snippet(" The most important towns after Montevideo\n" +
            " were Salto and Paysandú, but even today they still\n" +
            " have each only about 80,000 inhabitants. The\n" +
            " remaining urban centres are still more modest\n" +
            " and often even have fewer than 10,000 inhabi-\n" +
            " tants. They fulfil mainly a commercial and admin-\n" +
            " istrative function and also serve as the residence of\n" +
            " estancieros', who leave the daily running of their\n" +
            " ranches to managers. With the exception to some\n" +
            " extent of Paysandú, no industry");
    Snippet s12 = new Snippet(" The only settlement wih a real urban character\n" +
            " is Montevideo. Althoug the whole country had a\n" +
            " population of only 2.76 million in 1975, no less\n" +
            " than 1.23 million people lived in the capital.\n" +
            " Consequently, its population is about 16 times as\n" +
            " large as that of the second city, Salto. Montevideo\n" +
            " calls the tune in many fields. This pronounced\n" +
            " primacy is partly the result of the city being ahead\n" +
            " at the moment of independence, and partly of the\n" +
            " attraction which the capital has always been able\n" +
            " to exert, in a small and easily accessible terrority,\n" +
            " on economic activities and facilities of all kinds\n" +
            " and, through them, on the population. A regular");
    Snippet s13 = new Snippet(" In about 1 950 Uruguay had a very unbalanced\n" +
            " economy. Although it could already supply many\n" +
            " of its own needs in arable products and simple\n" +
            " manufactured goods, 90-95% of exports were still\n" +
            " accounted for by products from the primary\n" +
            " sector (mainly wool, meat and hides). It goes\n" +
            " without saying that the one-sided character of the\n" +
            " export trade made the country vulnerable and\n" +
            " dependent upon international price fluctuations\n" +
            " and changes in international demand");
    Snippet s14 = new Snippet(" It should not be concluded that Uruguay in\n" +
            " about 1 950 was a normal' Latin American\n" +
            " country. When the majority of Latin American\n" +
            " states after the Second World War could be\n" +
            " described without hesitation as 'underdeveloped',\n" +
            " Uruguay was known as the Switzerland of Latin\n" +
            " America.");
    Snippet s15 = new Snippet(" Although the country was completely depen-\n" +
            " dent in the economic field on the export of a few\n" +
            " primary products, until the early 1950s the\n" +
            " volume of exports, prices and rate of exchange\n" +
            " were such that the balance of payment was in\n" +
            " Uruguay's favour. Inflation was very low. Many\n" +
            " countries even regarded Uruguay after the war as\n" +
            " such a healthy and stable country that they\n" +
            " deposited a lot of capital in the Uruguayan banks.\n" +
            " Moreover, Uruguay was successful at that time in\n" +
            " reducing considerably the Bristish investments.\n" +
            " Politically, Uruguay was regarded as a model.\n" +
            " While many other Latin American countries were\n" +
            " regularly convulsed by civil war, revolution and\n" +
            " violence, peace and order prevailed in Uruguay.\n" +
            " Democracy functioned there in an exemplary\n" +
            " manner");
    Snippet s16 = new Snippet(" Uruguay, too, after its independence was for a\n" +
            " long time the scene of political disturbances, but\n" +
            " these came to and end under the progressive\n" +
            " president Botile y Ordonez (1903-1907). During\n" +
            " his second term of office, in particular (1911-\n" +
            " 1915), many administrative reforms were carried\n" +
            " out or set in train and the foundation was laid for\n" +
            " a properly functioning democratic system, which\n" +
            " was respected and valued by all Uruguayos' in\n" +
            " the period around 1.950. The army was a small\n" +
            " one and did not concern itself with political\n" +
            " matters");
    Snippet s17 = new Snippet(" Latin American countries after the Second World\n" +
            " War. Batlle y Ordonez believed that much of the\n" +
            " unrest in his country was related to the contrast\n" +
            " between rich and poor. He and his followers of\n" +
            " the Colorado party, which ruled the country, tried\n" +
            " to abolish this by means of a comprehensive\n" +
            " welfare policy. Consequently, the life of the\n" +
            " Uruguayans in about 1 950 was characterised by\n" +
            " a high measure of security. The social provisions\n" +
            " were even better than in the majority of European\n" +
            " countries");
    Snippet s18 = new Snippet(" The symptoms of underdevelopment, which are\n" +
            " characteristic of Latin American countries, were\n" +
            " lacking. Thanks to a low birth rate (13.5 per\n" +
            " thousand) population growth was no more than\n" +
            " 1-1.3% per annum. Life expectancy approached\n" +
            " 70 years. The majority of Uruguayans were,\n" +
            " moreover, well-fed and (partly thanks to govern-\n" +
            " ment subsidies) generally reasonably or even well\n" +
            " housed. More than 90% of the population could\n" +
            " read and write. From the standpoint of welfare\n" +
            " many people belonged to the middle class. A\n" +
            " large part of this class was formed by salaried\n" +
            " workers, employed in service trades, and by\n" +
            " officials. In order to reduce unemployment and\n" +
            " raise the level of welfare Batlle and his followers\n" +
            " created many jobs, particularly in the government\n" +
            " sector. The government's extended welfare policy\n" +
            " indeed also made this desirable");
    Snippet s19 = new Snippet(" After 1 955 an increasingly serious crisis deve-\n" +
            " loped in Uruguay. The vital agricultural export\n" +
            " trade ran into difficulties and this had conse-\n" +
            " quences for the whole economy. The causes of the\n" +
            " crisis are primarily of a structural nat");
    Snippet s20 = new Snippet(" The Uruguayan estancieros' had scarcely\n" +
            " begun to intensify their livestock farming. Accord-\n" +
            " ing to Prost (1977) the cattle and sheep popula-\n" +
            " tion had already been fully improved in about\n" +
            " 1935 by the introduction of new breeds and\n" +
            " certain other measures (such as vaccination) and,\n" +
            " moreover, all the suitable grazing land had been\n" +
            " occupied. Since then the livestock farming has\n" +
            " been little more than the continuation of a routine.\n" +
            " Consequently an expansion of the livestock popu-\n" +
            " lation was scarcely any longer possible an");
    List<Map<String, Double>> lds = new ArrayList<>();
    lds.add(s1.distribution());
    lds.add(s2.distribution());
    lds.add(s3.distribution());
    lds.add(s4.distribution());
    lds.add(s5.distribution());
    lds.add(s6.distribution());
    lds.add(s7.distribution());
    lds.add(s8.distribution());
    lds.add(s9.distribution());
    lds.add(s10.distribution());
    lds.add(s11.distribution());
    lds.add(s12.distribution());
    lds.add(s13.distribution());
    lds.add(s14.distribution());
    lds.add(s15.distribution());
    lds.add(s16.distribution());
    lds.add(s17.distribution());
    lds.add(s18.distribution());
    lds.add(s19.distribution());
    lds.add(s20.distribution());
    System.out.println(KeywordExtractor.extractKeywords(Arrays.asList("sheep", "populations", "reduction"),lds));
    List<Snippet> corpus = List.of(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, s17, s18, s19, s20);
    RankGraph rg = new RankGraph(corpus, new Jaccardish());
    rg.populateEdges(List.of("sheep", "populations", "reduction"));
    PageRank pr = new PageRank(rg);
    List<RankVertex> ranked = pr.pageRank();
    for (RankVertex rv : ranked) {
      System.out.println('\n' + rv.getValue().getSnippet().getOriginalText());
    }
    assertEquals(true, true);
  }
}
