#!/usr/bin/python3

import sys
import os
import json


def main():
    if len(sys.argv) != 4:
        print("error: give file as argument.\nUsage: $ python3 %s /path/to/ny_FIPS-file.csv /path/to/ny_geo-file.tab /path/to/ny_elect-file.tab" % __file__)
        sys.exit()

    fips_file = sys.argv[1]
    geo_file = sys.argv[2]
    elect_file = sys.argv[3]

    if not os.path.isfile(geo_file) or not os.path.isfile(elect_file):
        print("error: '%s' or '%s' is not a file." % (geo_file, elect_file))
        sys.exit()

    # Parse the FIPS data into a dict
    # Creates dict that associates the FIPS # with County Name.
    parsed_fips_data = ny_parse_fips_file(fips_file)
    # print(parsed_fips_data)

    # Parse the GEO data
    # Associates the FIPS & County Name to the precincts in the geo file.
    parsed_precinct_data = ny_parse_geo_data(geo_file, parsed_fips_data)
    # print(parsed_precinct_data)

    # Parse the Election data
    # Associates the prior data with Town/City/or NYC Borough name
    parsed_elect_data = ny_parse_elect_data(elect_file, parsed_precinct_data)
    print(json.dumps(parsed_elect_data))


def ny_parse_elect_data(elect_file, parsed_data):
    with open(elect_file) as fp:
        for cnt, line in enumerate(fp):
            if cnt == 0:
                continue

            parts = line.split("\t")

            # Elect data doesn't have FIPS # in a row.
            # So, instead, we'll find the county in our data struture by name. (to get the FIP)
            # County names aren't repeated in NYS, so this should be an OK method.
            # county_fip = next(i for i, county in enumerate(
            #     parsed_data) if county["name"] == parts[0].lower())
            for i, (fips, county_data) in enumerate(parsed_data.items()):
                # special cases for nyc boroughs
                # election data puts them all under "new york city"
                nyc_boroughs_to_county = {
                    "queens": "queens",
                    "brooklyn": "kings",
                    "manhattan": "new york",
                    "bronx": "bronx",
                    "staten island": "richmond",
                }

                read_county_name = parts[0].lower().strip('\"')
                if read_county_name == "new york city":
                    if parts[1].lower().strip('\"') in nyc_boroughs_to_county:
                        read_county_name = nyc_boroughs_to_county[parts[1].lower().strip(
                            '\"')]

                if county_data["county_name"].lower() == read_county_name:
                    # loop thru the precincts in a county
                    # find the precinct that has the same "election_district_(geosrc)" as our parts.
                    for j, precinct in enumerate(county_data["precincts"]):
                        if precinct["election_district_(geosrc)"] == parts[4].lower().strip('\"'):
                            area_name = parts[1].lower().strip('\"')
                            area_type = "borough"

                            if area_name[-2:] == " t":
                                area_name = area_name[:len(area_name) - 2]
                                area_type = "town"
                            elif area_name[-2:] == " c":
                                area_name = area_name[:len(area_name) - 2]
                                area_type = "city"

                            parsed_data[fips]["precincts"][j]["area_name"] = area_name
                            parsed_data[fips]["precincts"][j]["area_type"] = area_type
                            parsed_data[fips]["precincts"][j]["election_district_or_assembly_district"] = parts[3].lower().strip(
                                '\"')
                            parsed_data[fips]["precincts"][j]["assembly_district"] = parts[5].lower().strip(
                                '\"')
                            parsed_data[fips]["precincts"][j]["election_district"] = parts[6].lower().strip(
                                '\"')
                            parsed_data[fips]["precincts"][j]["congressional_district"] = parts[7].lower().strip(
                                '\"')
                            parsed_data[fips]["precincts"][j]["senate_district"] = parts[8].lower().strip(
                                '\"')
                            parsed_data[fips]["precincts"][j]["county_legislative_district"] = parts[9].lower().strip(
                                '\"')
    return parsed_data


def ny_parse_geo_data(geo_file, parsed_data):
    with open(geo_file) as fp:
        for cnt, line in enumerate(fp):
            if cnt == 0:
                continue

            parts = line.split("\t")
            county_fip = int((parts[3])[:5])
            parsed_data[county_fip].setdefault("precincts", list())
            precinct_data = dict()
            precinct_data["w-AKA-localCindex"] = int(parts[2])
            precinct_data["geoid10-AKA-countyfips_localCindex"] = int(parts[3])
            precinct_data["name10-AKA-global_index"] = parts[5].lower().strip('\"')
            precinct_data["election_district_(geosrc)"] = parts[6].lower().strip(
                '\"')
            parsed_data[county_fip]["precincts"].append(precinct_data)

    return parsed_data


def ny_parse_fips_file(fips_file):
    fips_data = dict()

    with open(fips_file) as fp:
        for cnt, line in enumerate(fp):
            parts = line.split(",")
            county_info = dict()
            county_info["county_name"] = parts[1].lower()
            fips_data[int(parts[0])] = county_info

    return fips_data


if __name__ == '__main__':
    main()
