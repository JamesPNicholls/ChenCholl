import requests
import json
burn = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?keyword=hotels&location=49.2488%20-122.9805&radius=50000&key=AIzaSyCCuOwCSOBZm1GASRaEhGgevAiTZ-bHUWQ"
van = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=49.28427179278537%20-123.12616800571902&radius=50000&types=hotels&key=AIzaSyCCuOwCSOBZm1GASRaEhGgevAiTZ-bHUWQ"

def main():
    my_jj={}
    my_json ={"name": [], "location":[], "address":[], "picture":[]}

    response = requests.get(burn)
    data = response.text
    parsed = json.loads(data)
    a = 0
    for i in parsed['results']:
        my_json["name"] = i["name"]
        my_json["location"]=i['geometry']['location']
        my_json["picture"]=i['photos'][0]['photo_reference']
        my_json['address']=i['vicinity']
        my_jj[a]=my_json.copy()
        a = a + 1

    response = requests.get(van)
    data = response.text
    parsed = json.loads(data)

    for i in parsed['results']:
        my_json["name"] = i["name"]
        my_json["location"]=i['geometry']['location']
        my_json["picture"]=i['photos'][0]['photo_reference']
        my_json['address']=i['vicinity']
        my_jj[a]=my_json.copy()
        a = a + 1

    big_json ={'hotels': my_jj}
    

    with open("hotel_data.json", "w") as outfile:
        json.dump(big_json,outfile)

main()