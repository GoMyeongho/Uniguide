import axios from "axios";
import Commons from "../util/Common";
import axiosInstance from "./AxiosInstance";
const Capstone = "";

// return 값을 반환할때 객체를 풀어서 반환하지말고 component 개별적으로 객체를 풀어서 사용할 것
const PsWriteApi = {

    // 자소서 DB 저장
    savePS: async (psWriteId, formData) => {
        const token = localStorage.getItem("accessToken");
        try {
            console.log([...formData])
            const memberId = formData.get("memberId");
            if (!memberId) {
                console.error("memberId가 없습니다.");
                alert("로그인이 필요합니다.");
                return;
            }
            const psWriteReqDto = {
                memberId: memberId,
                psName: formData.get("ps_name"),
            };
            const sections = [];
            for (const [key, value] of formData.entries()) {
                if (key.includes("sections")) {
                    console.log(sections);
                    const sectionIndex = key.match(/\d+/)?.[0];
                    if (sectionIndex !== undefined) {
                        if (!sections[sectionIndex]) {
                            sections[sectionIndex] = { psTitle: "", psContent: "", psContentsId: "", sectionsNum: sectionIndex };
                        }
                        if (key.includes("psTitle")) {
                            sections[sectionIndex].psTitle = value;
                        } else if (key.includes("psContent")) {
                            sections[sectionIndex].psContent = value;
                        } else if (key.includes("id")) {
                            sections[sectionIndex].psContentsId = value;
                        }
                    }
                }
            }

            const psContentsReqDtoList = sections.map(section => ({
                psTitle: section.psTitle,
                psContent: section.psContent,
                psContentsId: section.psContentsId,
                sectionsNum: section.sectionsNum,
            }));
            console.log(psContentsReqDtoList);

            const response = await axiosInstance.post(Capstone + `/write/save/${psWriteId}`, {
                psWriteReqDto: psWriteReqDto,
                psContentsReqDtoList: psContentsReqDtoList
            });
            return response.data;
        } catch (error) {
            console.error("API_자기소개서 저장 실패:", error);
            throw error;
        }
    },
    loadPsWrite : async (psWriteId) => {
        return await axiosInstance.get(Capstone + `/write/load/${psWriteId}`)
    },
    newPsWrite : async () => {
        return await axiosInstance.get(Capstone + `/write/make`)
    },

    getPsList : async () => {
        return await axiosInstance.get(Capstone + `/write/list/get`)
    },

    delPs: async (psWriteId) => {
        return await axios.delete(Capstone + `/write/del/${psWriteId}`)
    }
};

export default PsWriteApi;