package driver;

import genetic.Chromosome;

import java.awt.Dimension;

import ui.ArenaFrame;
import arena.Arena;
import arena.objects.AbstractShip;
import arena.objects.ships.AsteroidTurret;
import arena.objects.ships.NNShip;
import arena.objects.ships.SmartHunter;

public class TrainedNNShipDemo 
{
	private int hiddenCount = 3;
	private int hiddenDensity = 9;
	public TrainedNNShipDemo() 
	{
		//		AbstractShip asteroidHunter = new DerpShip();//

		double[] weights = { 0.29120510333394267,
				0.44088226195943536,
				0.28892544438294254,
				-0.5574599709244914,
				0.10604608246479641,
				0.07815539936858773,
				0.46117248250435283,
				-0.08189386720234759,
				-0.4793627339244476,
				-0.3429737378564115,
				0.48021563656759914,
				-0.8049116420995847,
				0.09872345988793718,
				0.29697984321132354,
				-0.5511756921820417,
				0.10699126501864309,
				-0.15625168842037596,
				-0.837365222999318,
				-0.800541846022178,
				0.5662321248945401,
				-0.8980881618372617,
				-0.7113136607794943,
				0.5351371066638473,
				0.1515397715022183,
				-0.9141486543831896,
				-0.0998416296386454,
				-0.9152074352175381,
				-0.9402859073289401,
				-0.5783333797645444,
				-0.7436918416208492,
				0.5767904783751018,
				-0.458099770406705,
				-0.29934430809356494,
				-0.8339362764753583,
				-0.9885203909613902,
				-0.9884469032133395,
				0.4922665857241453,
				0.13994509647601916,
				0.5780250997960171,
				0.44965190252265286,
				-0.4310704339989554,
				0.09737730644777831,
				0.9130718279539304,
				-0.6870214060826311,
				0.3921896831353422,
				0.8288497222156984,
				-0.47261765591308624,
				0.2777231286910984,
				-0.7493331459062413,
				0.3199405312951339,
				0.7929209014250108,
				-0.19607770273671943,
				-0.9296853375208469,
				-0.22318334593681988,
				0.02386998283783126,
				0.9369192773358008,
				0.39165235148049915,
				0.012708457028615339,
				0.1806857041697537,
				0.9446813554612863,
				-0.03430962878253585,
				-0.46218716661591175,
				-0.6261533658922496,
				0.44586372633685434,
				-0.698707512203953,
				-0.6687356169698079,
				-0.5685408408866042,
				-0.03253729286747731,
				-0.6711909579591718,
				0.0954343739446698,
				-0.6103589971813467,
				0.5945424502136777,
				0.34888970624569116,
				0.35511999628966306,
				-0.6331206888906024,
				-0.4480378078302578,
				0.4775350967074059,
				-0.7081047368971664,
				-0.5532545106963705,
				0.7580551271435754,
				-0.6217597930764693,
				-0.49648088332413676,
				0.7343294851407064,
				0.6027169021096943,
				-0.7780366551775423,
				-0.9876656078978535,
				-0.09074713884112962,
				-0.2767727155978026,
				0.2904130002035672,
				0.3498429465008176,
				-0.1680571741481407,
				0.6821108658964513,
				-0.4791966330079397,
				-0.7319589775119134,
				0.7350527527739158,
				0.9029011057143648,
				0.6427496116507337,
				-0.6709263953155132,
				-0.6728019182860216,
				0.31302518913905153,
				-0.34254634401069217,
				-0.2986949596375642,
				0.19577133045741757,
				-0.8134511210790305,
				0.131651989151217,
				-0.07184366494305128,
				0.6901984732881368,
				0.04720758858606311,
				0.13677752828407397,
				0.9963514166276793,
				-0.7048686661521049,
				-0.6896146995512071,
				-0.15406782919633355,
				-0.5702020209852655,
				-0.8794531367420355,
				0.1614273815112106,
				-0.16877871880697504,
				0.9164798957040743,
				0.8455643139025092,
				-0.36254748410267534,
				-0.45899966339015696,
				0.8296127623590026,
				0.38568475548636716,
				0.2739611241650248,
				0.6935778997682287,
				-0.7074177202022675,
				-0.38512572776349285,
				-0.5255573393814452,
				0.6844006095609045,
				0.5916064486294418,
				-0.3621720377816302,
				-0.27748434358967733,
				-0.7031913001686401,
				0.7894064964170358,
				0.6115688148333458,
				-0.012699531608212467,
				-0.5229763959807671,
				-0.7989543231520829,
				-0.24650719260607012,
				0.08836975767777044,
				-0.5943786579718416,
				0.5111166709703172,
				0.6742644615094556,
				-0.14377781929677413,
				-0.01689353203430921,
				0.9103962971018513,
				-0.29065559060292445,
				0.7123915736931511,
				0.2153044854173981,
				0.7475785335822309,
				-0.428451636463577,
				-0.6357519539873046,
				-0.4813939457104057,
				-0.9067748163864856,
				0.8449220203737208,
				-0.12683603661710108,
				-0.7911057452780944,
				0.43951931506792585,
				-0.28365640723503016,
				0.5805514196291373,
				-0.5573155887288167,
				0.4885752000400744,
				0.8958228025101459,
				0.47901833907861413,
				0.22836566238992295,
				-0.5741065519236145,
				-0.5560175931481437,
				-0.9892954031624726,
				-0.8925195680949427,
				-0.9648722042275837,
				-0.35786455857661414,
				-0.10120567680192094,
				0.29849400573089924,
				-0.10709788886514238,
				-0.8245182573418193,
				0.4029189739472684,
				-0.952223304893862,
				0.33368277460747187,
				-0.619228837831407,
				-0.8419466656381002,
				-0.20001323755167544,
				-0.3263968350313563,
				0.7911237457163753,
				-0.06920559869918597,
				-0.4582400854020996,
				-0.05372506900877694,
				-0.8413633094915436,
				0.2138068296081327,
				-0.9997513863747218,
				0.1570150326458155,
				0.14482584811457389,
				-0.1862258652727179,
				-0.9571247586479664,
				0.007583772774420683,
				-0.5124790362536419,
				0.8575105101346864,
				0.45294069763834033,
				0.9074405836514301,
				-0.9295932829025088,
				0.7121824227420818,
				-0.017225841190790958,
				0.1562816099131794,
				0.7549540004341246,
				-0.8224889505934712,
				0.7022229337034386,
				0.9574127695941519,
				0.9162283186858391,
				0.241661204835103,
				-0.5546436491394332,
				0.3852571171155488,
				-0.8866430242898015,
				-0.1638346828440368,
				0.3555606601213931,
				-0.8871029188865598,
				-0.47812974922820484,
				0.4482889143683455,
				0.7880948969943343,
				-0.04174871788640344,
				-0.7360936896380551,
				0.3556620145743342,
				-0.4430548683402892,
				-0.5477016077158541,
				0.4709620800107952,
				-0.593796731430587,
				0.3226833982346552,
				0.6635849552334225,
				-0.21166877086669367,
				-0.020873997438653258,
				0.7526689423001167,
				0.5734119785294215,
				0.6162309934882925,
				0.6034903570733493,
				0.8625770539150166,
				0.3993810691757944,
				-0.6245747268480519,
				0.47733548110816404,
				-0.04485867834428148,
				-0.8348495262752603,
				-0.8887649365790009,
				0.20812607213351564,
				0.05259115162358441,
				-0.6201417179075243,
				-0.26579658703346876,
				-0.6004523649927712,
				-0.6461086788223938,
				0.3338479128130093,
				-0.4081032401710927,
				0.6521933699815814,
				-0.9486778481416283,
				-0.2222701033856671,
				-0.7369389411554974,
				-0.5021573680092468,
				-0.9213750821314355,
				-0.03967114477170797,
				0.33379013734562,
				0.46860361930315453,
				0.6173219028597812,
				-0.1219831958696802,
				-0.5349733082093305,
				0.5263017427009353,
				-0.1254343237214981,
				-0.1666320179967402,
				-0.264575254011,
				0.8197849729805421,
				0.3203985943633597,
				0.8519625304500408,
				-0.9685456358281306,
				0.3315389562241875,
				-0.19392062477796945,
				-0.768336416077211,
				0.9238104694509877,
				-0.8417059919673666,
				0.47324570541705424,
				0.5159812113507511,
				-0.4512044017095006,
				-0.5726804037146115,
				0.08163139253033935,
				0.24412342616608596,
				-0.4035362359202327








		};


		AbstractShip asteroidHunter = new AsteroidTurret();
		AbstractShip nnShip = new NNShip(hiddenCount, hiddenDensity,new Chromosome(weights));
		Arena demoArena = new Arena(10,1,5);

		demoArena.addShipToArena(asteroidHunter);
		demoArena.addShipToArena(nnShip);

		ArenaFrame frame = new ArenaFrame(demoArena, new Dimension(500,500));
	}

	public static void main(String[] args)
	{
		new TrainedNNShipDemo();
	}

}
