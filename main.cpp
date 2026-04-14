//Very simple totally random map generator
#include <iostream>
#include <random>
#include <cmath>
#include <iomanip>

int main() {
    int l, w;

    // Enter the size of the array
    std::cout << "Please enter the length of the map:";
    std::cin >> l;
    std::cout << "Please enter the width of the map:";
    std::cin >> w;

    //Create a map
    std::vector<std::vector<double>> map(l, std::vector<double>(w));

    //prepare for random number
    std::random_device rd;
    std::mt19937 gen(rd());
    std::uniform_real_distribution<> dist(-12.0, 10.0);

    //assign the height for the map
    for (int i = 0; i < l; i++) {
        for (int j = 0; j < w; j++) {
            map[i][j] = round(dist(gen) * 100.0) / 100.0;
        }
    }

    //Display the height
    std::cout << std::endl;
    for (int i = 0; i < map.size(); i++) {
        for (int j = 0; j < map[i].size(); j++) {
            std::cout << std::setw(8) << map[i][j];
        }
        std::cout << std::endl;
    }

    //Display the map
    std::cout << std::endl;
    for (int i = 0; i < map.size(); i++) {
        for (int j = 0; j < map[i].size(); j++) {
            //Higher than sea level
            if (map[i][j] >= 0) {
                if (map[i][j] > 8) {
                    std::cout << "⛰️";
                }
                else if (map[i][j] > 5) {
                    std::cout << "🌲";
                }
                else {
                    std::cout << "🏖️";
                }
            }
            //Less than sea level
            else {
                std::cout << "🌊";
            }
        }
        std::cout << std::endl;
    }
}