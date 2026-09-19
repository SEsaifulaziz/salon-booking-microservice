import React from "react";
import StarIcon from '@mui/icons-material/Star';
const SalonCard = () => {
    return (
        <div className="">
            <div className="w-56 md:w-80 rounded-md bg-slate-100">
                <img className="w-full h-full object-cover rounded-t-md"
                 src="https://cosmosalon.pk/wp-content/uploads/2023/11/Cosmo-Salon.jpg" alt="" />
                 <div className="p-5 space-y-2">
                    <h1>Cosmo Salon</h1>
                    <div className="text-white text-sm p-1 bg-green-700 rounded-full w-14
                    flex items-center justify-center gap-1">
                        4.5 <StarIcon sx={{ fontSize: "16px" }} />
                    </div>
                     <p>Profissional hair cut</p>
                     <p>abc..</p>
                 </div>
            </div>
        </div>
    );
}
export default SalonCard;