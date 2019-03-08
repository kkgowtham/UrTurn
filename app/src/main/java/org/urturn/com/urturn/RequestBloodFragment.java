package org.urturn.com.urturn;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestBloodFragment extends Fragment {


    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseReference;
Context context;
    private EditText mPatientName,mHospitalName,mTimeLeft,mNoofUnits;
    private Spinner mcitySpinner,mStateSpinner,mBloodGroup;
    String userName;
    private static final String KEY_STATE = "state";
    private static final String KEY_CITIES = "cities";
    String state;
    private Button submitBtn;
    public RequestBloodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_request_blood, container, false);
        mDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mDatabase.getReference();
        mPatientName=view.findViewById(R.id.br_name);
        mHospitalName=view.findViewById(R.id.br_hosptalname);
        submitBtn=view.findViewById(R.id.submit_btn_request_blood);
        mTimeLeft=view.findViewById(R.id.br_timewithin);
        mNoofUnits=view.findViewById(R.id.no_of_units);
        mcitySpinner=view.findViewById(R.id.cities_request_spinner);
        mStateSpinner=view.findViewById(R.id.state_request_spinner);
        mBloodGroup=view.findViewById(R.id.blood_group_spinner);
        parseJson();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.blood_group));
        mBloodGroup.setAdapter(adapter);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestBlood();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }
    private void parseJson() {
        final List<State> statesList = new ArrayList<>();
        final List<String> states = new ArrayList<>();
        String json="[{\"state\":\"Andaman and Nicobar Islands\",\"cities\":[\"Port Blair\"]},{\"state\":\"Andhra Pradesh\",\"cities\":[\"Adoni\",\"Amalapuram\",\"Anakapalle\",\"Anantapur\",\"Bapatla\",\"Bheemunipatnam\",\"Bhimavaram\",\"Bobbili\",\"Chilakaluripet\",\"Chirala\",\"Chittoor\",\"Dharmavaram\",\"Eluru\",\"Gooty\",\"Gudivada\",\"Gudur\",\"Guntakal\",\"Guntur\",\"Hindupur\",\"Jaggaiahpet\",\"Jammalamadugu\",\"Kadapa\",\"Kadiri\",\"Kakinada\",\"Kandukur\",\"Kavali\",\"Kovvur\",\"Kurnool\",\"Macherla\",\"Machilipatnam\",\"Madanapalle\",\"Mandapeta\",\"Markapur\",\"Nagari\",\"Naidupet\",\"Nandyal\",\"Narasapuram\",\"Narasaraopet\",\"Narsipatnam\",\"Nellore\",\"Nidadavole\",\"Nuzvid\",\"Ongole\",\"Palacole\",\"Palasa Kasibugga\",\"Parvathipuram\",\"Pedana\",\"Peddapuram\",\"Pithapuram\",\"Ponnur\",\"Proddatur\",\"Punganur\",\"Puttur\",\"Rajahmundry\",\"Rajam\",\"Rajampet\",\"Ramachandrapuram\",\"Rayachoti\",\"Rayadurg\",\"Renigunta\",\"Repalle\",\"Salur\",\"Samalkot\",\"Sattenapalle\",\"Srikakulam\",\"Srikalahasti\",\"Srisailam Project (Right Flank Colony) Township\",\"Sullurpeta\",\"Tadepalligudem\",\"Tadpatri\",\"Tanuku\",\"Tenali\",\"Tirupati\",\"Tiruvuru\",\"Tuni\",\"Uravakonda\",\"Venkatagiri\",\"Vijayawada\",\"Vinukonda\",\"Visakhapatnam\",\"Vizianagaram\",\"Yemmiganur\",\"Yerraguntla\"]},{\"state\":\"Arunachal Pradesh\",\"cities\":[\"Naharlagun\",\"Pasighat\"]},{\"state\":\"Assam\",\"cities\":[\"Barpeta\",\"Bongaigaon City\",\"Dhubri\",\"Dibrugarh\",\"Diphu\",\"Goalpara\",\"Guwahati\",\"Jorhat\",\"Karimganj\",\"Lanka\",\"Lumding\",\"Mangaldoi\",\"Mankachar\",\"Margherita\",\"Mariani\",\"Marigaon\",\"Nagaon\",\"Nalbari\",\"North Lakhimpur\",\"Rangia\",\"Sibsagar\",\"Silapathar\",\"Silchar\",\"Tezpur\",\"Tinsukia\"]},{\"state\":\"Bihar\",\"cities\":[\"Araria\",\"Arrah\",\"Arwal\",\"Asarganj\",\"Aurangabad\",\"Bagaha\",\"Barh\",\"Begusarai\",\"Bettiah\",\"Bhabua\",\"Bhagalpur\",\"Buxar\",\"Chhapra\",\"Darbhanga\",\"Dehri-on-Sone\",\"Dumraon\",\"Forbesganj\",\"Gaya\",\"Gopalganj\",\"Hajipur\",\"Jamalpur\",\"Jamui\",\"Jehanabad\",\"Katihar\",\"Kishanganj\",\"Lakhisarai\",\"Lalganj\",\"Madhepura\",\"Madhubani\",\"Maharajganj\",\"Mahnar Bazar\",\"Makhdumpur\",\"Maner\",\"Manihari\",\"Marhaura\",\"Masaurhi\",\"Mirganj\",\"Mokameh\",\"Motihari\",\"Motipur\",\"Munger\",\"Murliganj\",\"Muzaffarpur\",\"Narkatiaganj\",\"Naugachhia\",\"Nawada\",\"Nokha\",\"Patna\",\"Piro\",\"Purnia\",\"Rafiganj\",\"Rajgir\",\"Ramnagar\",\"Raxaul Bazar\",\"Revelganj\",\"Rosera\",\"Saharsa\",\"Samastipur\",\"Sasaram\",\"Sheikhpura\",\"Sheohar\",\"Sherghati\",\"Silao\",\"Sitamarhi\",\"Siwan\",\"Sonepur\",\"Sugauli\",\"Sultanganj\",\"Supaul\",\"Warisaliganj\"]},{\"state\":\"Chandigarh\",\"cities\":[\"Chandigarh\"]},{\"state\":\"Chhattisgarh\",\"cities\":[\"Ambikapur\",\"Bhatapara\",\"Bhilai Nagar\",\"Bilaspur\",\"Chirmiri\",\"Dalli-Rajhara\",\"Dhamtari\",\"Durg\",\"Jagdalpur\",\"Korba\",\"Mahasamund\",\"Manendragarh\",\"Mungeli\",\"Naila Janjgir\",\"Raigarh\",\"Raipur\",\"Rajnandgaon\",\"Sakti\",\"Tilda Newra\"]},{\"state\":\"Dadra and Nagar Haveli\",\"cities\":[\"Silvassa\"]},{\"state\":\"Delhi\",\"cities\":[\"Delhi\",\"New Delhi\"]},{\"state\":\"Goa\",\"cities\":[\"Mapusa\",\"Margao\",\"Marmagao\",\"Panaji\"]},{\"state\":\"Gujarat\",\"cities\":[\"Adalaj\",\"Ahmedabad\",\"Amreli\",\"Anand\",\"Anjar\",\"Ankleshwar\",\"Bharuch\",\"Bhavnagar\",\"Bhuj\",\"Chhapra\",\"Deesa\",\"Dhoraji\",\"Godhra\",\"Jamnagar\",\"Kadi\",\"Kapadvanj\",\"Keshod\",\"Khambhat\",\"Lathi\",\"Limbdi\",\"Lunawada\",\"Mahesana\",\"Mahuva\",\"Manavadar\",\"Mandvi\",\"Mangrol\",\"Mansa\",\"Mahemdabad\",\"Modasa\",\"Morvi\",\"Nadiad\",\"Navsari\",\"Padra\",\"Palanpur\",\"Palitana\",\"Pardi\",\"Patan\",\"Petlad\",\"Porbandar\",\"Radhanpur\",\"Rajkot\",\"Rajpipla\",\"Rajula\",\"Ranavav\",\"Rapar\",\"Salaya\",\"Sanand\",\"Savarkundla\",\"Sidhpur\",\"Sihor\",\"Songadh\",\"Surat\",\"Talaja\",\"Thangadh\",\"Tharad\",\"Umbergaon\",\"Umreth\",\"Una\",\"Unjha\",\"Upleta\",\"Vadnagar\",\"Vadodara\",\"Valsad\",\"Vapi\",\"Vapi\",\"Veraval\",\"Vijapur\",\"Viramgam\",\"Visnagar\",\"Vyara\",\"Wadhwan\",\"Wankaner\"]},{\"state\":\"Haryana\",\"cities\":[\"Bahadurgarh\",\"Bhiwani\",\"Charkhi Dadri\",\"Faridabad\",\"Fatehabad\",\"Gohana\",\"Gurgaon\",\"Hansi\",\"Hisar\",\"Jind\",\"Kaithal\",\"Karnal\",\"Ladwa\",\"Mahendragarh\",\"Mandi Dabwali\",\"Narnaul\",\"Narwana\",\"Palwal\",\"Panchkula\",\"Panipat\",\"Pehowa\",\"Pinjore\",\"Rania\",\"Ratia\",\"Rewari\",\"Rohtak\",\"Safidon\",\"Samalkha\",\"Sarsod\",\"Shahbad\",\"Sirsa\",\"Sohna\",\"Sonipat\",\"Taraori\",\"Thanesar\",\"Tohana\",\"Yamunanagar\"]},{\"state\":\"Himachal Pradesh\",\"cities\":[\"Mandi\",\"Nahan\",\"Palampur\",\"Shimla\",\"Solan\",\"Sundarnagar\"]},{\"state\":\"Jammu and Kashmir\",\"cities\":[\"Anantnag\",\"Baramula\",\"Jammu\",\"Kathua\",\"Punch\",\"Rajauri\",\"Sopore\",\"Srinagar\",\"Udhampur\"]},{\"state\":\"Jharkhand\",\"cities\":[\"Adityapur\",\"Bokaro Steel City\",\"Chaibasa\",\"Chatra\",\"Chirkunda\",\"Medininagar (Daltonganj)\",\"Deoghar\",\"Dhanbad\",\"Dumka\",\"Giridih\",\"Gumia\",\"Hazaribag\",\"Jamshedpur\",\"Jhumri Tilaiya\",\"Lohardaga\",\"Madhupur\",\"Mihijam\",\"Musabani\",\"Pakaur\",\"Patratu\",\"Phusro\",\"Ramgarh\",\"Ranchi\",\"Sahibganj\",\"Saunda\",\"Simdega\",\"Tenu dam-cum-Kathhara\"]},{\"state\":\"Karnataka\",\"cities\":[\"Adyar\",\"Afzalpur\",\"Arsikere\",\"Athni\",\"Bengaluru\",\"Belagavi\",\"Ballari\",\"Chikkamagaluru\",\"Davanagere\",\"Gokak\",\"Hubli-Dharwad\",\"Karwar\",\"Kolar\",\"Lakshmeshwar\",\"Lingsugur\",\"Maddur\",\"Madhugiri\",\"Madikeri\",\"Magadi\",\"Mahalingapura\",\"Malavalli\",\"Malur\",\"Mandya\",\"Mangaluru\",\"Manvi\",\"Mudalagi\",\"Mudabidri\",\"Mysore\",\"Muddebihal\",\"Mudhol\",\"Mulbagal\",\"Mundargi\",\"Nanjangud\",\"Nargund\",\"Navalgund\",\"Nelamangala\",\"Pavagada\",\"Piriyapatna\",\"Puttur\",\"Rabkavi Banhatti\",\"Raayachuru\",\"Ranebennuru\",\"Ramanagaram\",\"Ramdurg\",\"Ranibennur\",\"Robertson Pet\",\"Ron\",\"Sadalagi\",\"Sagara\",\"Sakaleshapura\",\"Sindagi\",\"Sanduru\",\"Sankeshwara\",\"Saundatti-Yellamma\",\"Savanur\",\"Sedam\",\"Shahabad\",\"Shahpur\",\"Shiggaon\",\"Shikaripur\",\"Shivamogga\",\"Surapura\",\"Shrirangapattana\",\"Sidlaghatta\",\"Sindhagi\",\"Sindhnur\",\"Sira\",\"Sirsi\",\"Siruguppa\",\"Srinivaspur\",\"Tarikere\",\"Tekkalakote\",\"Terdal\",\"Talikota\",\"Tiptur\",\"Tumkur\",\"Udupi\",\"Vijayapura\",\"Wadi\",\"Yadgir\"]},{\"state\":\"Kerala\",\"cities\":[\"Adoor\",\"Alappuzha\",\"Attingal\",\"Chalakudy\",\"Changanassery\",\"Cherthala\",\"Chittur-Thathamangalam\",\"Guruvayoor\",\"Kanhangad\",\"Kannur\",\"Kasaragod\",\"Kayamkulam\",\"Kochi\",\"Kodungallur\",\"Kollam\",\"Kottayam\",\"Kozhikode\",\"Kunnamkulam\",\"Malappuram\",\"Mattannur\",\"Mavelikkara\",\"Mavoor\",\"Muvattupuzha\",\"Nedumangad\",\"Neyyattinkara\",\"Nilambur\",\"Ottappalam\",\"Palai\",\"Palakkad\",\"Panamattom\",\"Panniyannur\",\"Pappinisseri\",\"Paravoor\",\"Pathanamthitta\",\"Peringathur\",\"Perinthalmanna\",\"Perumbavoor\",\"Ponnani\",\"Punalur\",\"Puthuppally\",\"Koyilandy\",\"Shoranur\",\"Taliparamba\",\"Thiruvalla\",\"Thiruvananthapuram\",\"Thodupuzha\",\"Thrissur\",\"Tirur\",\"Vaikom\",\"Varkala\",\"Vatakara\"]},{\"state\":\"Madhya Pradesh\",\"cities\":[\"Alirajpur\",\"Ashok Nagar\",\"Balaghat\",\"Bhopal\",\"Ganjbasoda\",\"Gwalior\",\"Indore\",\"Itarsi\",\"Jabalpur\",\"Lahar\",\"Maharajpur\",\"Mahidpur\",\"Maihar\",\"Malaj Khand\",\"Manasa\",\"Manawar\",\"Mandideep\",\"Mandla\",\"Mandsaur\",\"Mauganj\",\"Mhow Cantonment\",\"Mhowgaon\",\"Morena\",\"Multai\",\"Mundi\",\"Murwara (Katni)\",\"Nagda\",\"Nainpur\",\"Narsinghgarh\",\"Narsinghgarh\",\"Neemuch\",\"Nepanagar\",\"Niwari\",\"Nowgong\",\"Nowrozabad (Khodargama)\",\"Pachore\",\"Pali\",\"Panagar\",\"Pandhurna\",\"Panna\",\"Pasan\",\"Pipariya\",\"Pithampur\",\"Porsa\",\"Prithvipur\",\"Raghogarh-Vijaypur\",\"Rahatgarh\",\"Raisen\",\"Rajgarh\",\"Ratlam\",\"Rau\",\"Rehli\",\"Rewa\",\"Sabalgarh\",\"Sagar\",\"Sanawad\",\"Sarangpur\",\"Sarni\",\"Satna\",\"Sausar\",\"Sehore\",\"Sendhwa\",\"Seoni\",\"Seoni-Malwa\",\"Shahdol\",\"Shajapur\",\"Shamgarh\",\"Sheopur\",\"Shivpuri\",\"Shujalpur\",\"Sidhi\",\"Sihora\",\"Singrauli\",\"Sironj\",\"Sohagpur\",\"Tarana\",\"Tikamgarh\",\"Ujjain\",\"Umaria\",\"Vidisha\",\"Vijaypur\",\"Wara Seoni\"]},{\"state\":\"Maharashtra\",\"cities\":[\"Ahmednagar\",\"Akola\",\"Akot\",\"Amalner\",\"Ambejogai\",\"Amravati\",\"Anjangaon\",\"Arvi\",\"Aurangabad\",\"Bhiwandi\",\"Dhule\",\"Kalyan-Dombivali\",\"Ichalkaranji\",\"Kalyan-Dombivali\",\"Karjat\",\"Latur\",\"Loha\",\"Lonar\",\"Lonavla\",\"Mahad\",\"Malegaon\",\"Malkapur\",\"Mangalvedhe\",\"Mangrulpir\",\"Manjlegaon\",\"Manmad\",\"Manwath\",\"Mehkar\",\"Mhaswad\",\"Mira-Bhayandar\",\"Morshi\",\"Mukhed\",\"Mul\",\"Greater Mumbai\",\"Murtijapur\",\"Nagpur\",\"Nanded-Waghala\",\"Nandgaon\",\"Nandura\",\"Nandurbar\",\"Narkhed\",\"Nashik\",\"Navi Mumbai\",\"Nawapur\",\"Nilanga\",\"Osmanabad\",\"Ozar\",\"Pachora\",\"Paithan\",\"Palghar\",\"Pandharkaoda\",\"Pandharpur\",\"Panvel\",\"Parbhani\",\"Parli\",\"Partur\",\"Pathardi\",\"Pathri\",\"Patur\",\"Pauni\",\"Pen\",\"Phaltan\",\"Pulgaon\",\"Pune\",\"Purna\",\"Pusad\",\"Rahuri\",\"Rajura\",\"Ramtek\",\"Ratnagiri\",\"Raver\",\"Risod\",\"Sailu\",\"Sangamner\",\"Sangli\",\"Sangole\",\"Sasvad\",\"Satana\",\"Satara\",\"Savner\",\"Sawantwadi\",\"Shahade\",\"Shegaon\",\"Shendurjana\",\"Shirdi\",\"Shirpur-Warwade\",\"Shirur\",\"Shrigonda\",\"Shrirampur\",\"Sillod\",\"Sinnar\",\"Solapur\",\"Soyagaon\",\"Talegaon Dabhade\",\"Talode\",\"Tasgaon\",\"Thane\",\"Tirora\",\"Tuljapur\",\"Tumsar\",\"Uchgaon\",\"Udgir\",\"Umarga\",\"Umarkhed\",\"Umred\",\"Uran\",\"Uran Islampur\",\"Vadgaon Kasba\",\"Vaijapur\",\"Vasai-Virar\",\"Vita\",\"Wadgaon Road\",\"Wai\",\"Wani\",\"Wardha\",\"Warora\",\"Warud\",\"Washim\",\"Yavatmal\",\"Yawal\",\"Yevla\"]},{\"state\":\"Manipur\",\"cities\":[\"Imphal\",\"Lilong\",\"Mayang Imphal\",\"Thoubal\"]},{\"state\":\"Meghalaya\",\"cities\":[\"Nongstoin\",\"Shillong\",\"Tura\"]},{\"state\":\"Mizoram\",\"cities\":[\"Aizawl\",\"Lunglei\",\"Saiha\"]},{\"state\":\"Nagaland\",\"cities\":[\"Dimapur\",\"Kohima\",\"Mokokchung\",\"Tuensang\",\"Wokha\",\"Zunheboto\"]},{\"state\":\"Odisha\",\"cities\":[\"Balangir\",\"Baleshwar Town\",\"Barbil\",\"Bargarh\",\"Baripada Town\",\"Bhadrak\",\"Bhawanipatna\",\"Bhubaneswar\",\"Brahmapur\",\"Byasanagar\",\"Cuttack\",\"Dhenkanal\",\"Jatani\",\"Jharsuguda\",\"Kendrapara\",\"Kendujhar\",\"Malkangiri\",\"Nabarangapur\",\"Paradip\",\"Parlakhemundi\",\"Pattamundai\",\"Phulabani\",\"Puri\",\"Rairangpur\",\"Rajagangapur\",\"Raurkela\",\"Rayagada\",\"Sambalpur\",\"Soro\",\"Sunabeda\",\"Sundargarh\",\"Talcher\",\"Tarbha\",\"Titlagarh\"]},{\"state\":\"Puducherry\",\"cities\":[\"Karaikal\",\"Mahe\",\"Pondicherry\",\"Yanam\"]},{\"state\":\"Punjab\",\"cities\":[\"Amritsar\",\"Barnala\",\"Batala\",\"Bathinda\",\"Dhuri\",\"Faridkot\",\"Fazilka\",\"Firozpur\",\"Firozpur Cantt.\",\"Gobindgarh\",\"Gurdaspur\",\"Hoshiarpur\",\"Jagraon\",\"Jalandhar Cantt.\",\"Jalandhar\",\"Kapurthala\",\"Khanna\",\"Kharar\",\"Kot Kapura\",\"Longowal\",\"Ludhiana\",\"Malerkotla\",\"Malout\",\"Mansa\",\"Moga\",\"Mohali\",\"Morinda, India\",\"Mukerian\",\"Muktsar\",\"Nabha\",\"Nakodar\",\"Nangal\",\"Nawanshahr\",\"Pathankot\",\"Patiala\",\"Pattran\",\"Patti\",\"Phagwara\",\"Phillaur\",\"Qadian\",\"Raikot\",\"Rajpura\",\"Rampura Phul\",\"Rupnagar\",\"Samana\",\"Sangrur\",\"Sirhind Fatehgarh Sahib\",\"Sujanpur\",\"Sunam\",\"Talwara\",\"Tarn Taran\",\"Urmar Tanda\",\"Zira\",\"Zirakpur\"]},{\"state\":\"Rajasthan\",\"cities\":[\"Ajmer\",\"Alwar\",\"Bikaner\",\"Bharatpur\",\"Bhilwara\",\"Jaipur\",\"Jodhpur\",\"Lachhmangarh\",\"Ladnu\",\"Lakheri\",\"Lalsot\",\"Losal\",\"Makrana\",\"Malpura\",\"Mandalgarh\",\"Mandawa\",\"Mangrol\",\"Merta City\",\"Mount Abu\",\"Nadbai\",\"Nagar\",\"Nagaur\",\"Nasirabad\",\"Nathdwara\",\"Neem-Ka-Thana\",\"Nimbahera\",\"Nohar\",\"Nokha\",\"Pali\",\"Phalodi\",\"Phulera\",\"Pilani\",\"Pilibanga\",\"Pindwara\",\"Pipar City\",\"Prantij\",\"Pratapgarh\",\"Raisinghnagar\",\"Rajakhera\",\"Rajaldesar\",\"Rajgarh (Alwar)\",\"Rajgarh (Churu)\",\"Rajsamand\",\"Ramganj Mandi\",\"Ramngarh\",\"Ratangarh\",\"Rawatbhata\",\"Rawatsar\",\"Reengus\",\"Sadri\",\"Sadulshahar\",\"Sadulpur\",\"Sagwara\",\"Sambhar\",\"Sanchore\",\"Sangaria\",\"Sardarshahar\",\"Sawai Madhopur\",\"Shahpura\",\"Shahpura\",\"Sheoganj\",\"Sikar\",\"Sirohi\",\"Sojat\",\"Sri Madhopur\",\"Sujangarh\",\"Sumerpur\",\"Suratgarh\",\"Taranagar\",\"Todabhim\",\"Todaraisingh\",\"Tonk\",\"Udaipur\",\"Udaipurwati\",\"Vijainagar, Ajmer\"]},{\"state\":\"Tamil Nadu\",\"cities\":[\"Arakkonam\",\"Aruppukkottai\",\"Chennai\",\"Coimbatore\",\"Erode\",\"Gobichettipalayam\",\"Kancheepuram\",\"Karur\",\"Lalgudi\",\"Madurai\",\"Manachanallur\",\"Nagapattinam\",\"Nagercoil\",\"Namagiripettai\",\"Namakkal\",\"Nandivaram-Guduvancheri\",\"Nanjikottai\",\"Natham\",\"Nellikuppam\",\"Neyveli (TS)\",\"O' Valley\",\"Oddanchatram\",\"P.N.Patti\",\"Pacode\",\"Padmanabhapuram\",\"Palani\",\"Palladam\",\"Pallapatti\",\"Pallikonda\",\"Panagudi\",\"Panruti\",\"Paramakudi\",\"Parangipettai\",\"Pattukkottai\",\"Perambalur\",\"Peravurani\",\"Periyakulam\",\"Periyasemur\",\"Pernampattu\",\"Pollachi\",\"Polur\",\"Ponneri\",\"Pudukkottai\",\"Pudupattinam\",\"Puliyankudi\",\"Punjaipugalur\",\"Ranipet\",\"Rajapalayam\",\"Ramanathapuram\",\"Rameshwaram\",\"Rasipuram\",\"Salem\",\"Sankarankoil\",\"Sankari\",\"Sathyamangalam\",\"Sattur\",\"Shenkottai\",\"Sholavandan\",\"Sholingur\",\"Sirkali\",\"Sivaganga\",\"Sivagiri\",\"Sivakasi\",\"Srivilliputhur\",\"Surandai\",\"Suriyampalayam\",\"Tenkasi\",\"Thammampatti\",\"Thanjavur\",\"Tharamangalam\",\"Tharangambadi\",\"Theni Allinagaram\",\"Thirumangalam\",\"Thirupuvanam\",\"Thiruthuraipoondi\",\"Thiruvallur\",\"Thiruvarur\",\"Thuraiyur\",\"Tindivanam\",\"Tiruchendur\",\"Tiruchengode\",\"Tiruchirappalli\",\"Tirukalukundram\",\"Tirukkoyilur\",\"Tirunelveli\",\"Tirupathur\",\"Tirupathur\",\"Tiruppur\",\"Tiruttani\",\"Tiruvannamalai\",\"Tiruvethipuram\",\"Tittakudi\",\"Udhagamandalam\",\"Udumalaipettai\",\"Unnamalaikadai\",\"Usilampatti\",\"Uthamapalayam\",\"Uthiramerur\",\"Vadakkuvalliyur\",\"Vadalur\",\"Vadipatti\",\"Valparai\",\"Vandavasi\",\"Vaniyambadi\",\"Vedaranyam\",\"Vellakoil\",\"Vellore\",\"Vikramasingapuram\",\"Viluppuram\",\"Virudhachalam\",\"Virudhunagar\",\"Viswanatham\"]},{\"state\":\"Telangana\",\"cities\":[\"Adilabad\",\"Bellampalle\",\"Bhadrachalam\",\"Bhainsa\",\"Bhongir\",\"Bodhan\",\"Farooqnagar\",\"Gadwal\",\"Hyderabad\",\"Jagtial\",\"Jangaon\",\"Kagaznagar\",\"Kamareddy\",\"Karimnagar\",\"Khammam\",\"Koratla\",\"Kothagudem\",\"Kyathampalle\",\"Mahbubnagar\",\"Mancherial\",\"Mandamarri\",\"Manuguru\",\"Medak\",\"Miryalaguda\",\"Nagarkurnool\",\"Narayanpet\",\"Nirmal\",\"Nizamabad\",\"Palwancha\",\"Ramagundam\",\"Sadasivpet\",\"Sangareddy\",\"Siddipet\",\"Sircilla\",\"Suryapet\",\"Tandur\",\"Vikarabad\",\"Wanaparthy\",\"Warangal\",\"Yellandu\"]},{\"state\":\"Tripura\",\"cities\":[\"Agartala\",\"Belonia\",\"Dharmanagar\",\"Kailasahar\",\"Khowai\",\"Pratapgarh\",\"Udaipur\"]},{\"state\":\"Uttar Pradesh\",\"cities\":[\"Achhnera\",\"Agra\",\"Aligarh\",\"Allahabad\",\"Amroha\",\"Azamgarh\",\"Bahraich\",\"Chandausi\",\"Etawah\",\"Firozabad\",\"Fatehpur Sikri\",\"Hapur\",\"Hardoi\",\"Jhansi\",\"Kalpi\",\"Kanpur\",\"Khair\",\"Laharpur\",\"Lakhimpur\",\"Lal Gopalganj Nindaura\",\"Lalitpur\",\"Lalganj\",\"Lar\",\"Loni\",\"Lucknow\",\"Mathura\",\"Meerut\",\"Modinagar\",\"Moradabad\",\"Nagina\",\"Najibabad\",\"Nakur\",\"Nanpara\",\"Naraura\",\"Naugawan Sadat\",\"Nautanwa\",\"Nawabganj\",\"Nehtaur\",\"Niwai\",\"Noida\",\"Noorpur\",\"Obra\",\"Orai\",\"Padrauna\",\"Palia Kalan\",\"Parasi\",\"Phulpur\",\"Pihani\",\"Pilibhit\",\"Pilkhuwa\",\"Powayan\",\"Pukhrayan\",\"Puranpur\",\"Purquazi\",\"Purwa\",\"Rae Bareli\",\"Rampur\",\"Rampur Maniharan\",\"Rampur Maniharan\",\"Rasra\",\"Rath\",\"Renukoot\",\"Reoti\",\"Robertsganj\",\"Rudauli\",\"Rudrapur\",\"Sadabad\",\"Safipur\",\"Saharanpur\",\"Sahaspur\",\"Sahaswan\",\"Sahawar\",\"Sahjanwa\",\"Saidpur\",\"Sambhal\",\"Samdhan\",\"Samthar\",\"Sandi\",\"Sandila\",\"Sardhana\",\"Seohara\",\"Shahabad, Hardoi\",\"Shahabad, Rampur\",\"Shahganj\",\"Shahjahanpur\",\"Shamli\",\"Shamsabad, Agra\",\"Shamsabad, Farrukhabad\",\"Sherkot\",\"Shikarpur, Bulandshahr\",\"Shikohabad\",\"Shishgarh\",\"Siana\",\"Sikanderpur\",\"Sikandra Rao\",\"Sikandrabad\",\"Sirsaganj\",\"Sirsi\",\"Sitapur\",\"Soron\",\"Suar\",\"Sultanpur\",\"Sumerpur\",\"Tanda\",\"Thakurdwara\",\"Thana Bhawan\",\"Tilhar\",\"Tirwaganj\",\"Tulsipur\",\"Tundla\",\"Ujhani\",\"Unnao\",\"Utraula\",\"Varanasi\",\"Vrindavan\",\"Warhapur\",\"Zaidpur\",\"Zamania\"]},{\"state\":\"Uttarakhand\",\"cities\":[\"Bageshwar\",\"Dehradun\",\"Haldwani-cum-Kathgodam\",\"Hardwar\",\"Kashipur\",\"Manglaur\",\"Mussoorie\",\"Nagla\",\"Nainital\",\"Pauri\",\"Pithoragarh\",\"Ramnagar\",\"Rishikesh\",\"Roorkee\",\"Rudrapur\",\"Sitarganj\",\"Srinagar\",\"Tehri\"]},{\"state\":\"West Bengal\",\"cities\":[\"Adra\",\"Alipurduar\",\"Arambagh\",\"Asansol\",\"Baharampur\",\"Balurghat\",\"Bankura\",\"Darjiling\",\"English Bazar\",\"Gangarampur\",\"Habra\",\"Hugli-Chinsurah\",\"Jalpaiguri\",\"Jhargram\",\"Kalimpong\",\"Kharagpur\",\"Kolkata\",\"Mainaguri\",\"Malda\",\"Mathabhanga\",\"Medinipur\",\"Memari\",\"Monoharpur\",\"Murshidabad\",\"Nabadwip\",\"Naihati\",\"Panchla\",\"Pandua\",\"Paschim Punropara\",\"Purulia\",\"Raghunathpur\",\"Raghunathganj\",\"Raiganj\",\"Rampurhat\",\"Ranaghat\",\"Sainthia\",\"Santipur\",\"Siliguri\",\"Sonamukhi\",\"Srirampore\",\"Suri\",\"Taki\",\"Tamluk\",\"Tarakeswar\"]}]";
        try {
            JSONArray responseArray=new JSONArray(json);
            try {
                //Parse the JSON response array by iterating over it
                for (int i = 0; i < responseArray.length(); i++) {
                    JSONObject response = responseArray.getJSONObject(i);
                    String state = response.getString(KEY_STATE);
                    JSONArray cities = response.getJSONArray(KEY_CITIES);
                    List<String> citiesList = new ArrayList<>();
                    for (int j = 0; j < cities.length(); j++) {
                        citiesList.add(cities.getString(j));
                    }
                    statesList.add(new State(state, citiesList));
                    states.add(state);

                }
                final StateAdapter stateAdapter = new StateAdapter(context,
                        R.layout.state_list, R.id.spinnerText, statesList);
                mStateSpinner.setAdapter(stateAdapter);
                mStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        State cityDetails = stateAdapter.getItem(position);
                        state=cityDetails.getStateName();
                        List<String> cityList = cityDetails.getCities();
                        ArrayAdapter citiesAdapter = new ArrayAdapter<>(context,
                                R.layout.city_list, R.id.citySpinnerText, cityList);
                        mcitySpinner.setAdapter(citiesAdapter);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void requestBlood() {

        try {
            String email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
            userName=email.substring(0,email.indexOf('@'));
            BloodDonateModel bloodDonateModel=new BloodDonateModel(
                    mPatientName.getText().toString(),
                    mTimeLeft.getText().toString(),
                    mBloodGroup.getSelectedItem().toString(),
                    Integer.parseInt(mNoofUnits.getText().toString()),
                    mHospitalName.getText().toString(),
                    state,
                    mcitySpinner.getSelectedItem().toString(),
                    getDateTime()
            );
            mDatabaseReference.child("BloodRequests").child(userName).push().setValue(bloodDonateModel);
            mDatabaseReference.child("RequestBasedOnCities").child(mcitySpinner.getSelectedItem().toString()).push().setValue(bloodDonateModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(context,"Added",Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }
    private String getDateTime() {
        String date=java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        return date;
    }

}
