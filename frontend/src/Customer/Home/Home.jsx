import React from "react";
import Banner from "./Banner";
import HomeServiceCard from "./HomeServiceCard";
import { services } from "../../Data/services";

const Home = () => {
  return (
    <div className="space-y-20">
        <section>
            <Banner/>
        </section>

        <section className="space-y-10 lg:space-y-0 lg:flex items-center gap-5 px-20">
          <div className="w-full lg:w-1/2">
            <h1 className="text-2xl font-semibold pb-9">
              What are you looking for...?
            </h1>
            <div className=" flex flex-wrap justify-center items-center gap-5 ">
              {
                services.map((item) => (
                  <HomeServiceCard key={item.id} item={item}/>
                ))
              }
            </div>
          </div>
        </section>

    </div>
  );
};

export default Home
