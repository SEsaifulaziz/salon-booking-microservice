import React from 'react';

const Banner = () => {
    return (
        <div className="w-full relative h-[80vh]">
            <video
                className="w-full h-full object-cover"
                muted
                autoPlay
                src="https://booksy-public.s3.amazonaws.com/horizontal_.webm"
            />

            <div className="textPart absolute flex flex-col items-center justify-center inset-0 text-white z-20 space-y-3 px-5">
                <h1 className="text-5xl font-bold">
                    Be yourself, be beautiful
                </h1>

                <p className="text-slate-400 text-2xl text-center font-semibold">
                    Discover and Book Beauty, wellness near you
                </p>

                <input
                    type="text"
                    placeholder="Search for services, salons, spas..."
                    className="w-full md:w-1/2 p-3 rounded-full text-black outline-none"
                />
            </div>
        </div>
    );
};

export default Banner;