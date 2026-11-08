import requests


API_URL = "https://geocoding-api.open-meteo.com/v1/search"


def search_city(city):
    params = {
        "name": city,
        "count": 5,
        "language": "en",
        "format": "json"
    }

    try:
        response = requests.get(
            API_URL,
            params=params,
            timeout=10
        )
        response.raise_for_status()

        data = response.json()
        return data.get("results", [])

    except requests.RequestException as error:
        print(f"[ERROR] API request failed: {error}")
        return []


def get_coordinates(city):
    results = search_city(city)

    if not results:
        return None

    location = results[0]

    return {
        "name": location["name"],
        "country": location.get("country", "Unknown"),
        "latitude": location["latitude"],
        "longitude": location["longitude"]
    }